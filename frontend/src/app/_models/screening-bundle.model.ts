import {ScreeningInfo} from './screeninginfo.model';

export class ScreeningBundle {

    screeningDate: Date;
    screeningInfos: ScreeningInfo[];

    constructor(screeningDate: Date) {
        this.screeningDate = screeningDate;
        this.screeningInfos = [];
    }

}
