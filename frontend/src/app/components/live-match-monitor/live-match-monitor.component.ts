import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { CEPRecommendation, MatchResult } from '../../models/cep.model';
import { extractApiErrorMessage } from '../../services/api-error-message';
import { CEPApiService } from '../../services/cep-api.service';

@Component({
  selector: 'app-live-match-monitor',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './live-match-monitor.component.html',
  styleUrl: './live-match-monitor.component.css',
})
export class LiveMatchMonitorComponent {
  private readonly fb = inject(FormBuilder);
  private readonly cepApi = inject(CEPApiService);

  readonly resultOptions: { value: MatchResult; label: string }[] = [
    { value: 'WINNING', label: 'Winning' },
    { value: 'DRAW', label: 'Drawing' },
    { value: 'LOSING', label: 'Losing' },
  ];

  readonly form = this.fb.nonNullable.group({
    currentMinute: [0, [Validators.required, Validators.min(0), Validators.max(120)]],
    currentResult: ['DRAW' as MatchResult, Validators.required],
    ownTeamRedCards: [0, [Validators.required, Validators.min(0)]],
    opponentRedCards: [0, [Validators.required, Validators.min(0)]],
  });

  isStarting = false;
  isSubmitting = false;
  matchStarted = false;
  errorMessage = '';
  statusMessage = '';
  recommendations: CEPRecommendation[] = [];
  minimumMinute = 0;
  minimumOwnTeamRedCards = 0;
  minimumOpponentRedCards = 0;

  startMatch(): void {
    if (this.isStarting || this.matchStarted) {
      return;
    }

    this.isStarting = true;
    this.errorMessage = '';
    this.statusMessage = '';

    this.cepApi
      .startMatch()
      .pipe(finalize(() => (this.isStarting = false)))
      .subscribe({
        next: (recommendations) => {
          this.matchStarted = true;
          this.addRecommendations(recommendations);
          this.statusMessage = 'Match started. Send match updates when the game state changes.';
        },
        error: (error: unknown) => {
          this.errorMessage = this.extractErrorMessage(error);
        },
      });
  }

  submitMatchState(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid || this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.statusMessage = '';

    this.cepApi
      .processMatchState(this.form.getRawValue())
      .pipe(finalize(() => (this.isSubmitting = false)))
      .subscribe({
        next: (recommendations) => {
          this.addRecommendations(recommendations);
          this.rememberSubmittedState();
          this.statusMessage = recommendations.length
            ? 'New live tactical adjustment generated.'
            : 'No new tactical adjustment for this match state.';
        },
        error: (error: unknown) => {
          this.errorMessage = this.extractErrorMessage(error);
        },
      });
  }

  isInvalid(controlName: keyof typeof this.form.controls): boolean {
    const control = this.form.controls[controlName];
    return control.invalid && (control.dirty || control.touched);
  }

  displayValue(value?: string): string {
    if (!value) {
      return '';
    }

    return value
      .toLowerCase()
      .split('_')
      .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
      .join(' ');
  }

  adjustedInstructions(recommendation: CEPRecommendation): { label: string; value?: string }[] {
    return [
      { label: 'Mentality', value: recommendation.adjustedMentality },
      { label: 'Passing', value: recommendation.adjustedPassing },
      { label: 'Pressing', value: recommendation.adjustedPressing },
      { label: 'Defensive Line', value: recommendation.adjustedDefensiveLineHeight },
      { label: 'Transition', value: recommendation.adjustedTransition },
    ].filter((instruction) => !!instruction.value);
  }

  private addRecommendations(recommendations: CEPRecommendation[]): void {
    this.recommendations = [...this.recommendations, ...recommendations];
  }

  private rememberSubmittedState(): void {
    const state = this.form.getRawValue();

    this.minimumMinute = state.currentMinute;
    this.minimumOwnTeamRedCards = state.ownTeamRedCards;
    this.minimumOpponentRedCards = state.opponentRedCards;

    this.form.controls.currentMinute.setValidators([
      Validators.required,
      Validators.min(this.minimumMinute),
      Validators.max(120),
    ]);
    this.form.controls.ownTeamRedCards.setValidators([
      Validators.required,
      Validators.min(this.minimumOwnTeamRedCards),
    ]);
    this.form.controls.opponentRedCards.setValidators([
      Validators.required,
      Validators.min(this.minimumOpponentRedCards),
    ]);
    this.form.updateValueAndValidity();
  }

  private extractErrorMessage(error: unknown): string {
    return extractApiErrorMessage(
      error,
      'We could not process this match update right now. Please try again.'
    );
  }
}
