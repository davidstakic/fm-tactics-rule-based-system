import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TacticalInputComponent } from './pages/tactical-input/tactical-input.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TacticalInputComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
