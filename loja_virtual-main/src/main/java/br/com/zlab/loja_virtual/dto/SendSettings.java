package br.com.zlab.loja_virtual.dto;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;

@Data
public class SendSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<String> selectedCampaigns = new ArrayList<>();
    private ArrayList<String> selectedSegments = new ArrayList<>();
    private ArrayList<String> selectedSuppressions = new ArrayList<>();
    private ArrayList<String> excludedCampaigns = new ArrayList<>();
    private ArrayList<String> excludedSegments = new ArrayList<>();
    private ArrayList<String> selectedContacts = new ArrayList<>();
    private String timeTravel = "false";
    private String perfectTiming = "false";
}

