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
import { extractApiErrorMessage } from '../../services/api-error-message';
import { ForwardChainingApiService } from '../../services/forward-chaining-api.service';

const DEFAULT_TACTIC_FORM_VALUE = {
  teamStrength: 3,
  formLast5Matches: 'W-W-D-L-W',
  tacticalFitness: 75,
  physicalProfile: 'FAST' as PhysicalProfile,
  midfieldQuality: 'BALANCED' as MidfieldQuality,
  highLineCapability: true,
  attackType: 'WING_PLAY' as AttackType,
  opponentStrength: 3,
  playingStyle: 'POSSESSION_BASED' as PlayingStyle,
  lineEngagement: 'MID_BLOCK' as DefenseLineEngagement,
  weakness: 'NO_OBVIOUS_WEAKNESS' as OpponentWeakness,
  competitionType: 'LEAGUE' as CompetitionType,
  importance: 'MEDIUM' as MatchImportance,
  location: 'HOME' as LocationType,
};

@Component({
  selector: 'app-generate-tactic',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TacticalRecommendationComponent, LiveMatchMonitorComponent],
  templateUrl: './generate-tactic.component.html',
  styleUrl: './generate-tactic.component.css',
})
export class GenerateTacticComponent {
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
    teamStrength: [DEFAULT_TACTIC_FORM_VALUE.teamStrength, [Validators.required, Validators.min(1), Validators.max(5)]],
    formLast5Matches: [DEFAULT_TACTIC_FORM_VALUE.formLast5Matches, [Validators.required, Validators.pattern(/^[WDL](?:-[WDL]){4}$/i)]],
    tacticalFitness: [DEFAULT_TACTIC_FORM_VALUE.tacticalFitness, [Validators.required, Validators.min(0), Validators.max(100)]],
    physicalProfile: [DEFAULT_TACTIC_FORM_VALUE.physicalProfile, Validators.required],
    midfieldQuality: [DEFAULT_TACTIC_FORM_VALUE.midfieldQuality, Validators.required],
    highLineCapability: [DEFAULT_TACTIC_FORM_VALUE.highLineCapability, Validators.required],
    attackType: [DEFAULT_TACTIC_FORM_VALUE.attackType, Validators.required],
    opponentStrength: [DEFAULT_TACTIC_FORM_VALUE.opponentStrength, [Validators.required, Validators.min(1), Validators.max(5)]],
    playingStyle: [DEFAULT_TACTIC_FORM_VALUE.playingStyle, Validators.required],
    lineEngagement: [DEFAULT_TACTIC_FORM_VALUE.lineEngagement, Validators.required],
    weakness: [DEFAULT_TACTIC_FORM_VALUE.weakness, Validators.required],
    competitionType: [DEFAULT_TACTIC_FORM_VALUE.competitionType, Validators.required],
    importance: [DEFAULT_TACTIC_FORM_VALUE.importance, Validators.required],
    location: [DEFAULT_TACTIC_FORM_VALUE.location, Validators.required],
  });

  submit(): void {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      this.errorMessage = 'Please review the highlighted fields before generating a recommendation.';
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

  resetToDefaults(): void {
    this.form.reset(DEFAULT_TACTIC_FORM_VALUE);
    this.form.markAsPristine();
    this.form.markAsUntouched();
    this.errorMessage = '';
    this.recommendation = null;
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
    return extractApiErrorMessage(
      error,
      'We could not generate a recommendation right now. Please try again.'
    );
  }
}
