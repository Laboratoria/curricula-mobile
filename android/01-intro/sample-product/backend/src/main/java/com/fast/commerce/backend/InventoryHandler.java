package com.fast.commerce.backend;

import com.fast.commerce.backend.inventory.GetInventoryRequest;
import com.fast.commerce.backend.inventory.GetInventoryResponse;
import com.fast.commerce.backend.inventory.Inventory;


public class InventoryHandler implements ApiHandler<GetInventoryResponse, GetInventoryRequest> {

    @Override
    public GetInventoryResponse handle(GetInventoryRequest arg) {
        GetInventoryResponse response = new GetInventoryResponse();
        response.setProducts(Inventory.getProducts());
        return response;
    }
}
