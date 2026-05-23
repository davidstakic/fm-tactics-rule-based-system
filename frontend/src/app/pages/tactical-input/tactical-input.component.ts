import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { LiveMatchMonitorComponent } from '../../components/live-match-monitor/live-match-monitor.component';
import { TacticalRecommendationComponent } from '../../components/tactical-recommendation/tactical-recommendation.component';
import {
  AttackType,
  CompetitionType,
  DefenseLineEngagement,
  ForwardChainingRequest,
  LocationType,
  MatchImportance,
  MidfieldQuality,
  OpponentWeakness,
  PhysicalProfile,
  PlayingStyle,
  TacticalRecommendation,
} from '../../models/tactical-recommendation.model';
import { ForwardChainingApiService } from '../../services/forward-chaining-api.service';

@Component({
  selector: 'app-tactical-input',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TacticalRecommendationComponent, LiveMatchMonitorComponent],
  templateUrl: './tactical-input.component.html',
  styleUrl: './tactical-input.component.css',
})
export class TacticalInputComponent {
  private readonly fb = inject(FormBuilder);
  private readonly forwardChainingApi = inject(ForwardChainingApiService);

  recommendation: TacticalRecommendation | null = null;
  errorMessage = '';
  isSubmitting = false;

  readonly physicalProfiles = this.options<PhysicalProfile>([
    ['FAST', 'Fast'],
    ['STRONG', 'Strong'],
    ['AVERAGE', 'Average'],
  ]);
  readonly midfieldQualities = this.options<MidfieldQuality>([
    ['CREATIVE', 'Creative'],
    ['AGGRESSIVE', 'Aggressive'],
    ['BALANCED', 'Balanced'],
  ]);
  readonly attackTypes = this.options<AttackType>([
    ['WING_PLAY', 'Wide Play'],
    ['CENTRAL_PLAY', 'Central Play'],
    ['PRESSING_ATTACKERS', 'Pressing Forwards'],
  ]);
  readonly playingStyles = this.options<PlayingStyle>([
    ['POSSESSION_BASED', 'Possession'],
    ['DIRECT', 'Direct'],
    ['COUNTER_ATTACK', 'Counter-Attacking'],
  ]);
  readonly lineEngagements = this.options<DefenseLineEngagement>([
    ['HIGH_PRESS', 'High Press'],
    ['MID_BLOCK', 'Mid Block'],
    ['LOW_BLOCK', 'Low Block'],
  ]);
  readonly weaknesses = this.options<OpponentWeakness>([
    ['VULNERABLE_ON_FLANKS', 'Vulnerable on the Wings'],
    ['WEAK_AERIAL_DEFENSE', 'Poor Aerial Ability'],
    ['SLOW_DEFENDERS', 'Slow Defenders'],
    ['UNRELIABLE_GOALKEEPER', 'Unreliable Goalkeeper'],
    ['NO_OBVIOUS_WEAKNESS', 'No Clear Weakness'],
  ]);
  readonly competitionTypes = this.options<CompetitionType>([
    ['FRIENDLY', 'Friendly'],
    ['LEAGUE', 'League'],
    ['CUP', 'Cup'],
    ['CONTINENTAL', 'Continental Competition'],
  ]);
  readonly matchImportances = this.options<MatchImportance>([
    ['LOW', 'Low'],
    ['MEDIUM', 'Medium'],
    ['HIGH', 'High'],
  ]);
  readonly locations = this.options<LocationType>([
    ['HOME', 'Home'],
    ['AWAY', 'Away'],
    ['NEUTRAL', 'Neutral'],
  ]);

  readonly form = this.fb.nonNullable.group({
    teamStrength: [3, [Validators.required, Validators.min(1), Validators.max(5)]],
    formLast5Matches: ['W-W-D-L-W', [Validators.required, Validators.pattern(/^[WDL](?:-[WDL]){4}$/i)]],
    tacticalFitness: [75, [Validators.required, Validators.min(0), Validators.max(100)]],
    physicalProfile: ['FAST' as PhysicalProfile, Validators.required],
    midfieldQuality: ['BALANCED' as MidfieldQuality, Validators.required],
    highLineCapability: [true, Validators.required],
    attackType: ['WING_PLAY' as AttackType, Validators.required],
    opponentStrength: [3, [Validators.required, Validators.min(1), Validators.max(5)]],
    playingStyle: ['POSSESSION_BASED' as PlayingStyle, Validators.required],
    lineEngagement: ['MID_BLOCK' as DefenseLineEngagement, Validators.required],
    weakness: ['NO_OBVIOUS_WEAKNESS' as OpponentWeakness, Validators.required],
    competitionType: ['LEAGUE' as CompetitionType, Validators.required],
    importance: ['MEDIUM' as MatchImportance, Validators.required],
    location: ['HOME' as LocationType, Validators.required],
  });

  submit(): void {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      this.errorMessage = 'Please fix the highlighted input values.';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.recommendation = null;

    this.forwardChainingApi
      .recommend(this.toRequest())
      .pipe(finalize(() => (this.isSubmitting = false)))
      .subscribe({
        next: (recommendation) => {
          this.recommendation = recommendation;
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

  private toRequest(): ForwardChainingRequest {
    const value = this.form.getRawValue();

    return {
      teamProfile: {
        teamStrength: value.teamStrength,
        formLast5Matches: value.formLast5Matches.toUpperCase(),
        tacticalFitness: value.tacticalFitness,
        physicalProfile: value.physicalProfile,
        midfieldQuality: value.midfieldQuality,
        highLineCapability: value.highLineCapability,
        attackType: value.attackType,
      },
      opponentProfile: {
        opponentStrength: value.opponentStrength,
        playingStyle: value.playingStyle,
        lineEngagement: value.lineEngagement,
        weakness: value.weakness,
      },
      matchContext: {
        competitionType: value.competitionType,
        importance: value.importance,
        location: value.location,
      },
    };
  }

  private options<T extends string>(entries: [T, string][]): { value: T; label: string }[] {
    return entries.map(([value, label]) => ({ value, label }));
  }

  private extractErrorMessage(error: unknown): string {
    if (typeof error === 'object' && error !== null && 'error' in error) {
      const response = error as { error?: unknown; message?: unknown };

      if (typeof response.error === 'string' && response.error.trim()) {
        return response.error;
      }

      if (typeof response.message === 'string' && response.message.trim()) {
        return response.message;
      }
    }

    return 'Unable to generate recommendation. Check that the service project is running.';
  }
}
