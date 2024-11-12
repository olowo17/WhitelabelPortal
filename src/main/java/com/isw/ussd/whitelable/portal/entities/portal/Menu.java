package com.isw.ussd.whitelable.portal.entities.portal;

import jakarta.persistence.*;

@Entity
@Table(name = "Menus")
@NamedQueries({@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
        @NamedQuery(name = "Menu.findByID", query = "SELECT m FROM Menu m WHERE m.id = :menuID")
})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "router_link")
    private String routerLink;

    @Column(name = "href")
    private String href;

    @Column(name = "icon")
    private String icon;

    @Column(name = "target")
    private String target;

    @Column(name = "has_sub_menu")
    private boolean hasSubMenu;

    @ManyToOne
    private Menu parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isHasSubMenu() {
        return hasSubMenu;
    }

    public void setHasSubMenu(boolean hasSubMenu) {
        this.hasSubMenu = hasSubMenu;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }


}