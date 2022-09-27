
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

(defn- menu-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :pages.page-viewer])]
       [common/item-viewer-menu-bar :pages.page-viewer
                                    {:disabled?  viewer-disabled?
                                     :menu-items [{:label :overview}]}]))

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :pages.page-viewer])]
       (case current-view-id :overview [page-overview])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :pages.page-viewer])
        page-id          @(a/subscribe [:router/get-current-route-path-param :item-id])
        edit-item-uri     (str "/@app-home/pages/"page-id"/edit")]
       [common/item-viewer-control-bar :pages.page-viewer
                                       {:disabled?     viewer-disabled?
                                        :edit-item-uri edit-item-uri}]))

(defn- breadcrumbs
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :pages.page-viewer])
        page-name        @(a/subscribe [:db/get-item [:pages :page-viewer/viewed-item :name]])]
       [common/surface-breadcrumbs :pages.page-viewer/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :pages    :route "/@app-home/pages"}
                                             {:label page-name :placeholder :unnamed-page}]
                                    :disabled? viewer-disabled?}]))

(defn- label-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :pages.page-viewer])
        page-name        @(a/subscribe [:db/get-item [:pages :page-viewer/viewed-item :name]])]
       [common/surface-label :pages.page-viewer/view
                             {:disabled?   viewer-disabled?
                              :label       page-name
                              :placeholder :unnamed-page}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- page-viewer
  []
  [item-viewer/body :pages.page-viewer
                    {:auto-title?   true
                     :ghost-element #'common/item-viewer-ghost-view
                     :item-element  #'view-structure
                     :item-path     [:pages :page-viewer/viewed-item]
                     :label-key     :name}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:page #'page-viewer}])
