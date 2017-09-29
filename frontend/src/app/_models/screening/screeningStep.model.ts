import {Criteria} from "./criteria";
import {Location} from "../location.model";

export class ScreeningStep {

    id: string;
    name: string;
    location: Location;
    enabled: boolean;
    criteria: Criteria[] = [];

    constructor(location: Location){
        this.location = location;
        this.enabled = true;
    }

}
