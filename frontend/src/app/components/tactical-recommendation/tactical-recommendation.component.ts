import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import {
  TacticalExplanationStep,
  TacticalNote,
  TacticalRecommendation,
} from '../../models/tactical-recommendation.model';

interface InstructionView {
  label: string;
  value: string;
  explanation?: string;
}

interface FormationView {
  value: string;
  explanation?: string;
}

@Component({
  selector: 'app-tactical-recommendation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tactical-recommendation.component.html',
  styleUrl: './tactical-recommendation.component.css',
})
export class TacticalRecommendationComponent {
  @Input({ required: true }) recommendation!: TacticalRecommendation;

  readonly instructionLabels = [
    { key: 'passingDirectness', label: 'Passing', decision: 'Passing' },
    { key: 'pressingIntensity', label: 'Pressing', decision: 'Pressing' },
    { key: 'defensiveLineHeight', label: 'Defensive Line', decision: 'Defensive line' },
    { key: 'transition', label: 'Transition', decision: 'Transition after losing the ball' },
  ] as const;

  displayValue(value: string | undefined): string {
    if (!value) {
      return '-';
    }

    if (/^FORMATION_\d+$/.test(value)) {
      return value.replace(/^FORMATION_/, '').split('').join('-');
    }

    return value
      .replace(/^FORMATION_/, '')
      .replaceAll('_', ' ')
      .toLowerCase()
      .replace(/\b\w/g, (letter) => letter.toUpperCase());
  }

  formationImageUrl(): string {
    const formation = this.recommendation.basicSettings?.recommendedFormation;

    if (!formation) {
      return '/assets/formations/4-4-2.png';
    }

    const digits = formation.replace(/^FORMATION_/, '');
    const fileName = digits.split('').join('-');
    return `/assets/formations/${fileName}.png`;
  }

  formationView(): FormationView {
    return {
      value: this.displayValue(this.recommendation.basicSettings?.recommendedFormation),
      explanation: this.explanationFor('Formation')?.explanation,
    };
  }

  instructionViews(): InstructionView[] {
    const instructions = this.recommendation.teamInstructions;
    const rows: InstructionView[] = [
      {
        label: 'Mentality',
        value: this.displayValue(this.recommendation.basicSettings?.mentality),
        explanation: this.explanationFor('Mentality')?.explanation,
      },
    ];

    if (!instructions) {
      return rows;
    }

    return [
      ...rows,
      ...this.instructionLabels.map((instruction) => ({
        label: instruction.label,
        value: this.displayValue(instructions[instruction.key]),
        explanation: this.explanationFor(instruction.decision)?.explanation,
      })),
    ];
  }

  hasNotes(notes: TacticalNote[] | undefined): boolean {
    return Boolean(notes?.length);
  }

  private explanationFor(decision: string): TacticalExplanationStep | undefined {
    return this.recommendation.explanationSteps.find((step) => step.decision === decision);
  }
}
