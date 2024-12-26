import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chapter } from '../../models/chapter.model';

@Component({
  selector: 'app-chapter-item',
  imports: [CommonModule],
  templateUrl: './chapter-item.component.html',
  standalone: true,
  styleUrl: './chapter-item.component.scss'
})
export class ChapterItemComponent {
  @Input() chapter!: Chapter;
}
