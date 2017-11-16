import {Rubric} from "./rubric";

export class Criteria {

    id: string;
    name: string;
    screeningStepId: string;
    enabled: boolean = true;
    rubrics: Rubric[]
}