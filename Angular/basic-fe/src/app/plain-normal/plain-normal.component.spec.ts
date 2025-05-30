import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlainNormalComponent } from './plain-normal.component';

describe('PlainNormalComponent', () => {
  let component: PlainNormalComponent;
  let fixture: ComponentFixture<PlainNormalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlainNormalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlainNormalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
