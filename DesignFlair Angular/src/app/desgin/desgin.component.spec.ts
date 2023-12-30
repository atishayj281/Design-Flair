import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesginComponent } from './desgin.component';

describe('DesginComponent', () => {
  let component: DesginComponent;
  let fixture: ComponentFixture<DesginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DesginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DesginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
