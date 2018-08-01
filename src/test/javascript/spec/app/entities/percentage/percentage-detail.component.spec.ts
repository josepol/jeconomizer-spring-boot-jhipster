/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JeconomizerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PercentageDetailComponent } from '../../../../../../main/webapp/app/entities/percentage/percentage-detail.component';
import { PercentageService } from '../../../../../../main/webapp/app/entities/percentage/percentage.service';
import { Percentage } from '../../../../../../main/webapp/app/entities/percentage/percentage.model';

describe('Component Tests', () => {

    describe('Percentage Management Detail Component', () => {
        let comp: PercentageDetailComponent;
        let fixture: ComponentFixture<PercentageDetailComponent>;
        let service: PercentageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JeconomizerTestModule],
                declarations: [PercentageDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PercentageService,
                    JhiEventManager
                ]
            }).overrideTemplate(PercentageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PercentageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PercentageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Percentage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.percentage).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
