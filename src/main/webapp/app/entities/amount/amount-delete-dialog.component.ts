import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Amount } from './amount.model';
import { AmountPopupService } from './amount-popup.service';
import { AmountService } from './amount.service';

@Component({
    selector: 'jhi-amount-delete-dialog',
    templateUrl: './amount-delete-dialog.component.html'
})
export class AmountDeleteDialogComponent {

    amount: Amount;

    constructor(
        private amountService: AmountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.amountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'amountListModification',
                content: 'Deleted an amount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-amount-delete-popup',
    template: ''
})
export class AmountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amountPopupService: AmountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.amountPopupService
                .open(AmountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
