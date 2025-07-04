package com.pharmacy.backend.service;

import com.pharmacy.backend.model.GeneralProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralProductService {
    public List<GeneralProduct> getCardiologyProducts() {
        List<GeneralProduct> products = new ArrayList<>();
        products.add(new GeneralProduct("Aspirin", "Used to prevent heart attacks and strokes", "aspirin.jpg", 30));
        products.add(new GeneralProduct("Atorvastatin", "Lowers cholesterol", "atorvastatin.jpg", 50));
        products.add(new GeneralProduct("Clopidogrel", "Prevents blood clots", "clopidogrel.jpg", 70));
        products.add(new GeneralProduct("Ramipril", "Used for high blood pressure", "ramipril.jpg", 45));
        products.add(new GeneralProduct("Losartan", "Controls blood pressure", "losartan.jpg", 40));
        return products;
    }

    public List<GeneralProduct> getGeneralProducts() {
        List<GeneralProduct> products = new ArrayList<>();
        products.add(new GeneralProduct("Paracetamol", "Used for fever and pain relief.", "paracetamol.jpg", 25));
        products.add(new GeneralProduct("Amoxicillin", "Antibiotic for infections", "amoxicillin.jpg", 45));
        // ...add more general medicines
        return products;
    }

    public List<GeneralProduct> getGynProducts() {
        List<GeneralProduct> products = new ArrayList<>();
        products.add(new GeneralProduct("Duphaston", "Hormone for pregnancy and periods", "duphaston.jpg", 120));
        products.add(new GeneralProduct("Meprate", "Used to regulate menstrual cycle", "meprate.jpg", 60));
        products.add(new GeneralProduct("Ovastar", "Used in PCOS/PCOD", "ovastar.jpg", 75));
        products.add(new GeneralProduct("Clomid", "Fertility treatment for ovulation", "clomid.jpg", 90));
        return products;
    }
}
