import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TacticalInputComponent } from './tactical-input.component';

describe('TacticalInputComponent', () => {
  let component: TacticalInputComponent;
  let fixture: ComponentFixture<TacticalInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TacticalInputComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TacticalInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
