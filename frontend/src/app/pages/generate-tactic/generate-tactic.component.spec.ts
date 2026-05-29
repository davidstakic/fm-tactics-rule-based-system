import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateTacticComponent } from './generate-tactic.component';

describe('GenerateTacticComponent', () => {
  let component: GenerateTacticComponent;
  let fixture: ComponentFixture<GenerateTacticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateTacticComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateTacticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
