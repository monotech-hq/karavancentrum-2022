
(ns app.contents.frontend.lister.views
    (:require [app.common.frontend.api               :as common]
              [app.components.frontend.api           :as components]
              [app.contents.frontend.handler.helpers :as handler.helpers]
              [elements.api                          :as elements]
              [hiccup.api                            :as hiccup]
              [layouts.surface-a.api                 :as surface-a]
              [re-frame.api                          :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :contents.lister])]
          [common/item-lister-download-info :contents.lister {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-list-item
  ; @param (keyword) lister-id
  ; @param (map) lister-props
  ; @param (integer) item-dex
  ; @param (map) content-item
  [_ _ item-dex {:keys [body id modified-at name]}]
  (let [timestamp   @(r/subscribe [:x.activities/get-actual-timestamp modified-at])
        content-body (-> body handler.helpers/parse-content-body hiccup/to-string)]
       [components/item-list-row {:cells [[components/list-item-thumbnail {:icon :article :icon-family :material-icons-outlined}]
                                          [components/list-item-cell      {:rows [{:content name :placeholder :unnamed-content}
                                                                                  {:content content-body :placeholder "-" :color :muted :font-size :xs}]}]
                                          [components/list-item-gap       {:width 12}]
                                          [components/list-item-cell      {:rows [{:content timestamp :font-size :xs :color :muted}] :width 100}]
                                          [components/list-item-gap       {:width 12}]
                                          [components/list-item-button    {:label :open! :width 100 :on-click [:x.router/go-to! (str "/@app-home/contents/"id)]}]
                                          [components/list-item-gap       {:width 12}]]
                                  :border (if (not= item-dex 0) :top)}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content-list-header
  []
  (let [current-order-by @(r/subscribe [:item-lister/get-current-order-by :contents.lister])]
       [components/item-list-header ::content-list-header
                                    {:cells [{:width 84}
                                             {:label :name :order-by-key :name
                                              :on-click [:item-lister/order-items! :contents.lister :name]}
                                             {:width 12}
                                             {:label :modified :width 100 :order-by-key :modified-at
                                              :on-click [:item-lister/order-items! :contents.lister :modified-at]}
                                             {:width 12}
                                             {:width 100}
                                             {:width 12}]
                                     :border :bottom
                                     :order-by current-order-by}]))

(defn- content-lister
  []
  [common/item-lister :contents.lister
                      {:default-order-by  :modified-at/descending
                       :list-item-element #'content-list-item
                       :item-list-header  #'content-list-header
                       :items-path        [:contents :lister/downloaded-items]}])

(defn- body
  []
  [components/surface-box ::body
                          {:content [:<> [content-lister]
                                         [elements/horizontal-separator {:height :xxs}]]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :contents.lister])
        create-content-uri (str "/@app-home/contents/create")]
       [common/item-lister-create-item-button :contents.lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-content-uri}]))

(defn- search-field
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :contents.lister])]
       [common/item-lister-search-field :contents.lister
                                        {:disabled?   lister-disabled?
                                         :placeholder :search-in-contents
                                         :search-keys [:name]}]))

(defn- search-description
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :contents.lister])]
       [common/item-lister-search-description :contents.lister
                                              {:disabled? lister-disabled?}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :contents.lister])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :contents}]
                                        :disabled? lister-disabled?}]))

(defn- label
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :contents.lister])]
       [components/surface-label ::label
                                 {:disabled? lister-disabled?
                                  :label     :contents}]))

(defn- header
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :contents.lister])]
          [:<> [:div {:style {:display "flex" :justify-content "space-between" :flex-wrap "wrap" :grid-row-gap "24px"}}
                     [:div [label]
                           [breadcrumbs]]
                     [:div [create-item-button]]]
               [search-field]
               [search-description]]
          [components/ghost-view {:breadcrumb-count 2 :layout :box-surface-header}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) surface-id
  [_]
  [:<> [header]
       [body]
       [footer]])

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
