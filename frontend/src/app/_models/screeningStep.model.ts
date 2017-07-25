import {Criteria} from './criteria.model'

export class ScreeningStep {

    id: number;
    name: string;
    locationId: string;
    criterias: Criteria[] = [];
    enabled: boolean;

    constructor(locationId: string){
        this.locationId = locationId;
        this.enabled = true;
    }

}
