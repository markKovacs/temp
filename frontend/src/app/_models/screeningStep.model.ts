import {Criteria} from './criteria.model'

export class ScreeningStep {

    id: number;
    name: string;
    locationId: string;
    criterias: Criteria[] = [];
    enabled: boolean;
    comment: string;
    points: number;
    status: string;
    stepId: string;

    constructor(locationId: string){
        this.locationId = locationId;
        this.enabled = true;
    }

}
