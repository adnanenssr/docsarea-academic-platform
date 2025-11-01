package com.docsarea.dtos.invitation;

import java.util.List;

public class GetLinkPageDto {
    Integer numPages ;
    Long numElements ;
    List<GetInvitationLink> links ;

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Long getNumElements() {
        return numElements;
    }

    public void setNumElements(Long numElements) {
        this.numElements = numElements;
    }

    public List<GetInvitationLink> getLinks() {
        return links;
    }

    public void setLinks(List<GetInvitationLink> links) {
        this.links = links;
    }
}
