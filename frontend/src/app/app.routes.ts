import { Routes } from '@angular/router';
import { GenerateTacticComponent } from './pages/generate-tactic/generate-tactic.component';
import { CheckTacticComponent } from './pages/check-tactic/check-tactic.component';

export const routes: Routes = [
  { path: 'generate', component: GenerateTacticComponent },
  { path: 'check', component: CheckTacticComponent },
  { path: '', pathMatch: 'full', redirectTo: 'generate' },
  { path: '**', redirectTo: 'generate' },
];
