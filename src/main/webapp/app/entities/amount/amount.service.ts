import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Amount } from './amount.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AmountService {

    private resourceUrl = SERVER_API_URL + 'api/amounts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/amounts';

    constructor(private http: Http) { }

    create(amount: Amount): Observable<Amount> {
        const copy = this.convert(amount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(amount: Amount): Observable<Amount> {
        const copy = this.convert(amount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Amount> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Amount.
     */
    private convertItemFromServer(json: any): Amount {
        const entity: Amount = Object.assign(new Amount(), json);
        return entity;
    }

    /**
     * Convert a Amount to a JSON which can be sent to the server.
     */
    private convert(amount: Amount): Amount {
        const copy: Amount = Object.assign({}, amount);
        return copy;
    }
}
