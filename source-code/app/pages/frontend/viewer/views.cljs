
(ns app.pages.frontend.viewer.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [plugins.item-viewer.api :as item-viewer]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- page-overview
  [])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-viewer-ghost-view :pages.page-viewer
                                 {:padding "0 12px"}])

(defn- menu-bar
  []
  [common/item-viewer-menu-bar :pages.page-viewer
                               {:menu-items [{:label :overview :view-id :overview}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :pages.page-viewer])]
       [:<> [menu-bar]
            (case current-view-id :overview [page-overview])]))

(defn- page-viewer
  []
  [item-viewer/body :pages.page-viewer
                    {:auto-title?   true
                     :ghost-element #'ghost-view
                     :item-element  #'view-selector
                     :item-path     [:pages :page-viewer/viewed-item]
                     :label-key     :name}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [page-name @(a/subscribe [:db/get-item [:pages :page-viewer/viewed-item :name]])]
       [common/item-viewer-breadcrumbs :pages.page-viewer
                                       {:crumbs [{:label :app-home
                                                  :route "/@app-home"}
                                                 {:label :pages
                                                  :route "/@app-home/pages"}
                                                 {:label       page-name
                                                  :placeholder :unnamed-page}]}]))

(defn- label-bar
  []
  (let [page-name    @(a/subscribe [:db/get-item [:pages :page-viewer/viewed-item :name]])
        page-id      @(a/subscribe [:router/get-current-route-path-param :item-id])
        edit-item-uri (str "/@app-home/pages/"page-id"/edit")]
       [common/item-viewer-label-bar :pages.page-viewer
                                     {:edit-item-uri edit-item-uri
                                      :label         page-name
                                      :placeholder   :unnamed-page}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [page-viewer]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:page #'view-structure}])
