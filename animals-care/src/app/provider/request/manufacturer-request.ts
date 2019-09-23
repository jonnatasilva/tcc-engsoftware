export class ManufacturerRequest {
    manufacturerId: Number;
    name: String;

    constructor(manufacturerId: Number, name?: String) {
        this.manufacturerId = manufacturerId;
        this.name = name;
    }
}
