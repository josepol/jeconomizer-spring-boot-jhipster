import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Movement } from './movement.model';
import { MovementPopupService } from './movement-popup.service';
import { MovementService } from './movement.service';

@Component({
    selector: 'jhi-movement-delete-dialog',
    templateUrl: './movement-delete-dialog.component.html'
})
export class MovementDeleteDialogComponent {

    movement: Movement;

    constructor(
        private movementService: MovementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'movementListModification',
                content: 'Deleted an movement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movement-delete-popup',
    template: ''
})
export class MovementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movementPopupService: MovementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.movementPopupService
                .open(MovementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
