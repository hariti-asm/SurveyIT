import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chapter-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chapter-item.component.html',
  styleUrls: ['./chapter-item.component.scss']
})
export class ChapterItemComponent {
  @Input() chapter!: any;
  @Input() selectedSubChapterId: number | null = null;
  @Output() subChapterSelected = new EventEmitter<any>();

  isExpanded = false;

  toggleChapter(): void {
    this.isExpanded = !this.isExpanded;
  }

  onSubChapterClick(subChapter: any): void {
    this.subChapterSelected.emit(subChapter);
  }
}
