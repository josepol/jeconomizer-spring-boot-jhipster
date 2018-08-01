import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Percentage } from './percentage.model';
import { PercentagePopupService } from './percentage-popup.service';
import { PercentageService } from './percentage.service';

@Component({
    selector: 'jhi-percentage-delete-dialog',
    templateUrl: './percentage-delete-dialog.component.html'
})
export class PercentageDeleteDialogComponent {

    percentage: Percentage;

    constructor(
        private percentageService: PercentageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.percentageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'percentageListModification',
                content: 'Deleted an percentage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-percentage-delete-popup',
    template: ''
})
export class PercentageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private percentagePopupService: PercentagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.percentagePopupService
                .open(PercentageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
