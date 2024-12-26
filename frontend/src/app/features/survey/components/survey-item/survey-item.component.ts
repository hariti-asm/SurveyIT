import { Component, Input } from '@angular/core';
import { Survey } from '../../models/survey.model';
import { CommonModule } from '@angular/common';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-survey-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './survey-item.component.html',
})
export class SurveyItemComponent {
  @Input() survey!: Survey;
}
