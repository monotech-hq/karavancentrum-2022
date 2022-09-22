
(ns app.contents.frontend.viewer.views
    (:require [app.common.frontend.api :as common]
              [app-fruits.html         :as html]
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
       [elements/text ::content-body-value
                      {:color   :muted
                       :content (html/to-hiccup (str "<div>"content-body"</div>"))
                       :indent  {:vertical :xs}}]))

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
                                 {}])

(defn- menu-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])]
       [common/item-viewer-menu-bar :contents.content-viewer
                                    {:disabled?  viewer-disabled?
                                     :menu-items [{:label :overview}]}]))

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :contents.content-viewer])]
       (case current-view-id :overview [content-overview])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])
        content-id       @(a/subscribe [:router/get-current-route-path-param :item-id])
        edit-item-uri     (str "/@app-home/contents/"content-id"/edit")]
       [common/item-viewer-control-bar :contents.content-viewer
                                       {:disabled?     viewer-disabled?
                                        :edit-item-uri edit-item-uri}]))

(defn- breadcrumbs
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])
        content-name     @(a/subscribe [:db/get-item [:contents :content-viewer/viewed-item :name]])]
       [common/surface-breadcrumbs :contents.content-viewer/view
                                   {:crumbs [{:label :app-home   :route "/@app-home"}
                                             {:label :contents    :route "/@app-home/contents"}
                                             {:label content-name :placeholder :unnamed-content}]
                                    :disabled? viewer-disabled?}]))

(defn- label-bar
  []
  (let [viewer-disabled? @(a/subscribe [:item-viewer/viewer-disabled? :contents.content-viewer])
        content-name     @(a/subscribe [:db/get-item [:contents :content-viewer/viewed-item :name]])]
       [common/surface-label :contents.content-viewer/view
                             {:disabled?   viewer-disabled?
                              :label       content-name
                              :placeholder :unnamed-content}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [label-bar]
        [breadcrumbs]
        [elements/horizontal-separator {:size :xxl}]
        [menu-bar]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- content-viewer
  []
  [item-viewer/body :contents.content-viewer
                    {:auto-title?   true
                     :ghost-element #'ghost-view
                     :item-element  #'view-structure
                     :item-path     [:contents :content-viewer/viewed-item]
                     :label-key     :name}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'content-viewer}])
