

import {ScreeningStep} from "./screeningStep.model";
import {ApplicantsScreeningStepCriteria} from "./applicants-step-criteria.model";

export class ApplicantsScreeningStep {

    id: string;
    step: ScreeningStep;
    interviewer: string;
    points: number;
    comment: string;
    status: string;
    criteria: ApplicantsScreeningStepCriteria[];

    average = 0;
}