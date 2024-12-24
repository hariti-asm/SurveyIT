import { SurveyEdition } from './survey-edition.model';
import { Question } from './question.model';

export interface Chapter {
    id: number;
    title: string;
    surveyEdition: SurveyEdition;
    parentChapter?: Chapter;
    subChapters?: Chapter[];
    questions?: Question[];
}