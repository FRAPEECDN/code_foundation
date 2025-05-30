import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlainEntityComponent } from './plain-entity.component';

describe('PlainEntityComponent', () => {
  let component: PlainEntityComponent;
  let fixture: ComponentFixture<PlainEntityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlainEntityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlainEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
