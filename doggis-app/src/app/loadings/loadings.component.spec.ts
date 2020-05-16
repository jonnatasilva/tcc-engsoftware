import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingsComponent } from './loadings.component';

describe('LoadingsComponent', () => {
  let component: LoadingsComponent;
  let fixture: ComponentFixture<LoadingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
