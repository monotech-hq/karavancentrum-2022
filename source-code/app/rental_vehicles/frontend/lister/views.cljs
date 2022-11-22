
(ns app.rental-vehicles.frontend.lister.views
    (:require [app.common.frontend.api     :as common]
              [app.components.frontend.api :as components]
              [elements.api                :as elements]
              [layouts.surface-a.api       :as surface-a]
              [re-frame.api                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :rental-vehicles.lister])]
          [common/item-lister-download-info :rental-vehicles.lister {}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-list-item
  ; @param (keyword) lister-id
  ; @param (map) lister-props
  ; @param (integer) item-dex
  ; @param (map) vehicle-item
  [_ _ item-dex {:keys [id modified-at name thumbnail]} {:keys [handle-attributes item-attributes]}]
  (let [timestamp @(r/subscribe [:x.activities/get-actual-timestamp modified-at])]
       [components/item-list-row {:cells [[components/list-item-gap         {:width 12}]
                                          [components/list-item-drag-handle {:indent {:left :xs} :drag-attributes handle-attributes}]
                                          [components/list-item-gap         {:width 12}]
                                          [components/list-item-thumbnail   {:thumbnail (:media/uri thumbnail)}]
                                          [components/list-item-gap         {:width 12}]
                                          [components/list-item-cell        {:rows [{:content name :placeholder :unnamed-rental-vehicle}]}]
                                          [components/list-item-gap         {:width 12}]
                                          [components/list-item-cell        {:rows [{:content timestamp :font-size :xs :color :muted}] :width 100}]
                                          [components/list-item-gap         {:width 12}]
                                          [components/list-item-button      {:label :open! :width 100 :on-click [:x.router/go-to! (str "/@app-home/rental-vehicles/"id)]}]
                                          [components/list-item-gap         {:width 12}]]
                                  :border (if (not= item-dex 0) :top)
                                  :drag-attributes item-attributes}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-list-header
  []
  (let [current-order-by @(r/subscribe [:item-lister/get-current-order-by :rental-vehicles.lister])]
       [components/item-list-header ::vehicle-list-header
                                    {:cells [{:width 12}
                                             {:width 24}
                                             {:width 12}
                                             {:width 84}
                                             {:width 12}
                                             {:label :name :order-by-key :name
                                              :on-click [:item-lister/order-items! :rental-vehicles.lister :name]}
                                             {:width 12}
                                             {:label :modified :width 100 :order-by-key :modified-at
                                              :on-click [:item-lister/order-items! :rental-vehicles.lister :modified-at]}
                                             {:width 12}
                                             {:width 100}
                                             {:width 12}]
                                     :border :bottom
                                     :order-by current-order-by}]))

(defn- vehicle-lister
  []
  [common/item-lister :rental-vehicles.lister
                      {:default-order-by  :modified-at/descending
                       :list-item-element #'vehicle-list-item
                       :item-list-header  #'vehicle-list-header
                       :items-path        [:rental-vehicles :lister/downloaded-items]
                       :sortable?         true}])

(defn- body
  []
  [components/surface-box ::body
                          {:content [:<> [vehicle-lister]
                                         [elements/horizontal-separator {:height :xxs}]]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])
        create-vehicle-uri (str "/@app-home/rental-vehicles/create")]
       [common/item-lister-create-item-button :rental-vehicles.lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-vehicle-uri}]))

(defn- search-field
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/item-lister-search-field :rental-vehicles.lister
                                        {:disabled?   lister-disabled?
                                         :placeholder :search-in-rental-vehicles
                                         :search-keys [:name]}]))

(defn- search-description
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [common/item-lister-search-description :rental-vehicles.lister
                                              {:disabled? lister-disabled?}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [components/surface-breadcrumbs ::breadcrumbs
                                       {:crumbs [{:label :app-home :route "/@app-home"}
                                                 {:label :rental-vehicles}]
                                        :disabled? lister-disabled?}]))

(defn- label
  []
  (let [lister-disabled? @(r/subscribe [:item-lister/lister-disabled? :rental-vehicles.lister])]
       [components/surface-label ::label
                                 {:disabled? lister-disabled?
                                  :label     :rental-vehicles}]))

(defn- header
  []
  (if-let [first-data-received? @(r/subscribe [:item-lister/first-data-received? :rental-vehicles.lister])]
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
