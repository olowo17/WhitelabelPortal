package com.isw.ussd.whitelable.portal.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuInfoDto {

    private Long id;

    private String title;

    private String routerLink;

    private String href;

    private String icon;

    private String target;

    private Boolean hasSubMenu;

    private Long parentID;

    private Long roleID;

    @Override
    public String toString() {

        return String.format("title: %s, router link: %s", this.title, this.routerLink);

    }

}
