import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RubricFormComponent } from './rubric-form.component';

describe('RubricFormComponent', () => {
  let component: RubricFormComponent;
  let fixture: ComponentFixture<RubricFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RubricFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RubricFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
