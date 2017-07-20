import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";
import { DatePipe } from '@angular/common';
import {Message} from 'primeng/primeng';

@Component({
    moduleId: module.id,
    templateUrl: 'calendar.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarComponent {

    public users: User[] = [];
    public dates: Date[] = [];
    public loaded: boolean = false;
    public dateSelectorOn: boolean = false;
    public date: Date;
    public chosenDate: Date;
    public sourceList: User[] = [];
    public targetList: User[] = [];
    public isPostingGroupTimes: boolean = false;
    public isPostingIndividualTimes: boolean = false;
    public groupTimesSet: boolean = false;
    public individualTimesSet: boolean = false;
    public messages: Message[] = [];

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        this.fetchUsers();
    }

    loadDates(){
        for (let user of this.users) {
            if (user.group != null && !this.dates.includes(user.group)) {
                this.dates.push(user.group)
            }
        }
        this.loaded = true;
    }

    sortDates(date1, date2){
        if (date1.getTime() > date2.getTime()) { return 1; }
        if (date1.getTime() < date2.getTime()) { return -1; }
        return 0;
    }

    toggleDateSelector(){
        this.dateSelectorOn = !this.dateSelectorOn;
        this.date = null;
    }

    addDate(){
        if (this.date && !this.dates.includes(this.date)){
            this.dates.push(this.date);
            this.dateSelectorOn = false;
        }
    }

    selectDate(date){
        this.chosenDate = date;
        this.groupTimesSet = null;
        this.sourceList = this.users.filter((user) => user.group == null);
        this.targetList = this.users.filter((user) => user.group == date);
    }

    showGroupTimes(){
        return this.chosenDate != null && !this.isPostingGroupTimes && !this.groupTimesSet;
    }

    showIndividualTimes(){
        return this.groupTimesSet && !this.isPostingIndividualTimes && this.targetList.length > 0;
    }

    showLoading(){
      return this.isPostingIndividualTimes || this.isPostingGroupTimes;
    }

    setGroupTimes(){
        for (let user of this.targetList) {
            user.group = this.chosenDate;
            console.log(user);
        }
    }

    saveGroupTimes(){
        this.setGroupTimes();
        this.postGroupTimes();
    }

    saveIndividualTimes(){
        this.postIndividualTimes();
    }

    getIndividual(user){
      return user.individual ? user.individual : user.group
    }

    fetchUsers(){
      return setTimeout(()=>{
          let myDate1 = new Date(1500540616000);
          let myDate2 = new Date(1497540616000);
          let user1 = new User(); user1.id = 1; user1.gender = 'male'; user1.name = 'Kovács Béla'; user1.age = 18; user1.group = myDate1; user1.individual = myDate1;
          let user2 = new User(); user2.id = 2; user2.gender = 'male'; user2.name = 'Varga Lajos'; user2.age = 25; user2.group = null; user2.individual = null;
          let user3 = new User(); user3.id = 3; user3.gender = 'male'; user3.name = 'Nagy Kázmér'; user3.age = 22; user3.group = myDate1; user3.individual = null;
          let user4 = new User(); user4.id = 4; user4.gender = 'male'; user4.name = 'Magyar Oszkár'; user4.age = 28; user4.group = myDate2; user4.individual = myDate2;
          let user5 = new User(); user5.id = 5; user5.gender = 'female'; user5.name = 'Horváth Etelka'; user5.age = 16; user5.group = null; user5.individual = null;
          let user6 = new User(); user6.id = 6; user6.gender = 'female'; user6.name = 'Lengyel Ramóna'; user6.age = 23; user6.group = null; user6.individual = null;
          let user7 = new User(); user7.id = 7; user7.gender = 'male'; user7.name = 'Kiss Ottokár'; user7.age = 23; user7.group = null; user7.individual = null;
          let user8 = new User(); user8.id = 8; user8.gender = 'male'; user8.name = 'Kis Kevin'; user8.age = 42; user8.group = null; user8.individual = null;
          let user9 = new User(); user9.id = 9; user9.gender = 'female'; user9.name = 'Nagy Teréz'; user9.age = 27; user9.group = null; user9.individual = null;
          this.users = [ user1, user2, user3, user4, user5, user6, user7, user8, user9 ];
          this.loadDates();
      }, 1000);
    }

    postGroupTimes(){
        let sendData = {date: null, applicants: []};
        sendData.date = this.chosenDate;
        for (let user of this.targetList) { sendData.applicants.push(user.id) }
        console.log("should post this:", sendData);
        this.isPostingGroupTimes = true;
        return setTimeout(() => {
            this.isPostingGroupTimes = false;
            this.groupTimesSet = true;
            this.messages.push({severity:'info', summary:'Group times saved.', detail:"(for " + this.chosenDate + ")"});
            if (this.targetList.length == 0) { this.chosenDate = null; };
        }, 1000)
    }

    postIndividualTimes(){
        let sendData = [];
        for (let user of this.targetList) { sendData.push({id: user.id, individual: user.individual}) }
        console.log("should post this:", {individualTimes: sendData});
        this.isPostingIndividualTimes = true;
        this.individualTimesSet = false;
        return setTimeout(() => {
            this.messages.push({severity:'info', summary:'Individual times saved.', detail:"(for " + this.chosenDate + ")"});
            this.isPostingIndividualTimes = false;
            this.individualTimesSet = true;
            this.chosenDate = null;
            this.groupTimesSet = false;
        }, 1000)
    }
}
