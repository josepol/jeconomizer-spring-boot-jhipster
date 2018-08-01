/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JeconomizerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AmountDetailComponent } from '../../../../../../main/webapp/app/entities/amount/amount-detail.component';
import { AmountService } from '../../../../../../main/webapp/app/entities/amount/amount.service';
import { Amount } from '../../../../../../main/webapp/app/entities/amount/amount.model';

describe('Component Tests', () => {

    describe('Amount Management Detail Component', () => {
        let comp: AmountDetailComponent;
        let fixture: ComponentFixture<AmountDetailComponent>;
        let service: AmountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JeconomizerTestModule],
                declarations: [AmountDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AmountService,
                    JhiEventManager
                ]
            }).overrideTemplate(AmountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Amount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.amount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
