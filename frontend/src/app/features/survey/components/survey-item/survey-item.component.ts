import { Component, Input } from '@angular/core';
import { Survey } from '../../models/survey.model';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-survey-item',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="survey-item">
      <h3>{{survey.title}}</h3>
      <p>{{survey.description}}</p>
    </div>
  `
})
export class SurveyItemComponent {
  @Input() survey!: Survey;
}
