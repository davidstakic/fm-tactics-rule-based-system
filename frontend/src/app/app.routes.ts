import { Routes } from '@angular/router';
import { TacticalInputComponent } from './pages/tactical-input/tactical-input.component';
import { TacticCheckComponent } from './pages/tactic-check/tactic-check.component';

export const routes: Routes = [
  { path: 'generate', component: TacticalInputComponent },
  { path: 'check', component: TacticCheckComponent },
  { path: '', pathMatch: 'full', redirectTo: 'generate' },
  { path: '**', redirectTo: 'generate' },
];
