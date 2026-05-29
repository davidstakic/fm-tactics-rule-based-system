import { CommonModule } from '@angular/common';
import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BackwardChainingResponse } from '../../models/backward-chaining.model';
import { extractApiErrorMessage } from '../../services/api-error-message';
import { BackwardChainingApiService } from '../../services/backward-chaining-api.service';

type GoalCategory =
  | 'FORMATION'
  | 'MENTALITY'
  | 'PASSING'
  | 'PRESSING'
  | 'DEFENSIVE_LINE'
  | 'TRANSITION';

interface GoalOption {
  label: string;
  value: string;
}

interface GoalCategoryOption {
  label: string;
  value: GoalCategory;
  options: GoalOption[];
}

@Component({
  selector: 'app-check-tactic',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './check-tactic.component.html',
  styleUrl: './check-tactic.component.css',
})
export class CheckTacticComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly api = inject(BackwardChainingApiService);
  private readonly destroyRef = inject(DestroyRef);

  readonly goalCategories: GoalCategoryOption[] = [
    {
      label: 'Formation',
      value: 'FORMATION',
      options: [
        { label: '3-4-3', value: 'FORMATION_343' },
        { label: '3-5-2', value: 'FORMATION_352' },
        { label: '3-4-1-2', value: 'FORMATION_3412' },
        { label: '4-4-2', value: 'FORMATION_442' },
        { label: '4-3-3', value: 'FORMATION_433' },
        { label: '4-2-3-1', value: 'FORMATION_4231' },
        { label: '4-1-4-1', value: 'FORMATION_4141' },
        { label: '4-3-1-2', value: 'FORMATION_4312' },
        { label: '4-5-1', value: 'FORMATION_451' },
        { label: '4-1-2-3', value: 'FORMATION_4123' },
        { label: '5-3-2', value: 'FORMATION_532' },
        { label: '5-2-3', value: 'FORMATION_523' },
        { label: '5-4-1', value: 'FORMATION_541' },
      ],
    },
    {
      label: 'Mentality',
      value: 'MENTALITY',
      options: [
        { label: 'Attacking', value: 'ATTACKING' },
        { label: 'Positive', value: 'POSITIVE' },
        { label: 'Balanced', value: 'BALANCED' },
        { label: 'Cautious', value: 'CAUTIOUS' },
        { label: 'Defensive', value: 'DEFENSIVE' },
      ],
    },
    {
      label: 'Passing',
      value: 'PASSING',
      options: [
        { label: 'Shorter', value: 'SHORTER' },
        { label: 'Standard', value: 'STANDARD' },
        { label: 'Direct', value: 'DIRECT' },
      ],
    },
    {
      label: 'Pressing',
      value: 'PRESSING',
      options: [
        { label: 'High', value: 'HIGH' },
        { label: 'Medium', value: 'MEDIUM' },
        { label: 'Low', value: 'LOW' },
      ],
    },
    {
      label: 'Defensive line',
      value: 'DEFENSIVE_LINE',
      options: [
        { label: 'High', value: 'HIGH' },
        { label: 'Standard', value: 'STANDARD' },
        { label: 'Low', value: 'LOW' },
      ],
    },
    {
      label: 'Transition',
      value: 'TRANSITION',
      options: [
        { label: 'Counter press', value: 'COUNTER_PRESS' },
        { label: 'Hold shape', value: 'HOLD_SHAPE' },
        { label: 'Regroup', value: 'REGROUP' },
      ],
    },
  ];

  readonly form = this.fb.nonNullable.group({
    category: ['FORMATION' as GoalCategory, Validators.required],
    targetValue: ['FORMATION_343', Validators.required],
  });

  response?: BackwardChainingResponse;
  errorMessage = '';
  isLoading = false;

  ngOnInit(): void {
    this.form.controls.category.valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((category) => {
        this.form.controls.targetValue.setValue(this.optionsFor(category)[0]?.value ?? '');
      });
  }

  get selectedGoalOptions(): GoalOption[] {
    return this.optionsFor(this.form.controls.category.value);
  }

  submit(): void {
    if (this.form.invalid || this.isLoading) {
      this.form.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.response = undefined;

    const { category, targetValue } = this.form.getRawValue();
    this.api.requirements({ targetGoal: `${category}:${targetValue}` }).subscribe({
      next: (response) => {
        this.response = response;
        this.isLoading = false;
      },
      error: (error: unknown) => {
        this.errorMessage = extractApiErrorMessage(
          error,
          'We could not check this tactical idea right now. Please try again.'
        );
        this.isLoading = false;
      },
    });
  }

  private optionsFor(category: GoalCategory): GoalOption[] {
    return this.goalCategories.find((goalCategory) => goalCategory.value === category)?.options ?? [];
  }
}
