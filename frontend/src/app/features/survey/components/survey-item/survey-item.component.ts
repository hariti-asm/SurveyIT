// survey-item.component.ts
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Survey } from '../../models/survey.model';

@Component({
  selector: 'app-survey-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './survey-item.component.html',
})
export class SurveyItemComponent {
  @Input() survey!: Survey;

  constructor(private router: Router) {}

  navigateToChapters(editionId: string) {
    console.log('Navigating to:', editionId);
    this.router.navigate(['editions', editionId, 'chapters'])
      .then(() => console.log('Navigation successful'))
      .catch(err => console.error('Navigation failed:', err));
  }
}
