
(ns app.contents.frontend.viewer.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [plugins.item-viewer.api :as item-viewer]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-info
  []
  [common/item-viewer-item-info :contents.content-viewer
                                {:indent {:top :l :vertical :xs}}])

(defn- content-visibility-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])]
       [elements/label ::content-visibility-label
                       {:content   :content-visibility
                        :disabled? viewer-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- content-visibility-value
  []
  (let [viewer-disabled?   @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])
        content-visibility @(a/subscribe [:db/get-item [:contents :content-viewer/viewed-item :visibility]])]
       [elements/label ::content-visibility-value
                       {:color     :muted
                        :content   (case content-visibility :public :public-content :private :private-content)
                        :disabled? viewer-disabled?
                        :font-size :xs
                        :indent    {:vertical :xs}}]))

(defn- content-visibility
  []
  [:<> [content-visibility-label]
       [content-visibility-value]])

(defn- content-body-label
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])]
       [elements/label ::content-body-label
                       {:content   :body
                        :disabled? viewer-disabled?
                        :indent    {:top :l :vertical :xs}}]))

(defn- content-body-value
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])
        content-body     @(a/subscribe [:db/get-item [:contents :content-viewer/viewed-item :body]])]
       content-body))

(defn- content-body
  []
  [:<> [content-body-label]
       [content-body-value]])

(defn- content-overview
  []
  [:<> [content-body]
       [content-visibility]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [common/item-viewer-ghost-view :contents.content-viewer
                                 {:padding "0 12px"}])

(defn- menu-bar
  []
  [common/item-viewer-menu-bar :contents.content-viewer
                               {:menu-items [{:label :overview :view-id :overview}]}])

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :contents.content-viewer])]
       [:<> [menu-bar]
            (case current-view-id :overview [content-overview])]))

(defn- content-viewer
  []
  [item-viewer/body :contents.content-viewer
                    {:auto-title?   true
                     :ghost-element #'ghost-view
                     :item-element  #'view-selector
                     :item-path     [:contents :content-viewer/viewed-item]
                     :label-key     :name}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [content-name @(a/subscribe [:db/get-item [:contents :content-viewer/viewed-item :name]])]
       [common/item-viewer-breadcrumbs :contents.content-viewer
                                       {:crumbs [{:label :app-home
                                                  :route "/@app-home"}
                                                 {:label :contents
                                                  :route "/@app-home/contents"}
                                                 {:label       content-name
                                                  :placeholder :unnamed-content}]}]))

(defn- label-bar
  []
  (let [content-name    @(a/subscribe [:db/get-item [:contents :content-viewer/viewed-item :name]])
        content-id      @(a/subscribe [:router/get-current-route-path-param :item-id])
        edit-item-uri    (str "/@app-home/contents/"content-id"/edit")]
       [common/item-viewer-label-bar :contents.content-viewer
                                     {:edit-item-uri edit-item-uri
                                      :label         content-name
                                      :placeholder   :unnamed-content}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [content-viewer]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
