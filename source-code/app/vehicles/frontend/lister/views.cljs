
(ns app.vehicles.frontend.lister.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [plugins.item-lister.api :as item-lister]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-item-structure
  [lister-id item-dex {:keys [modified-at name thumbnail]}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
       [common/list-item-structure lister-id item-dex
                                   {:cells [[common/list-item-thumbnail    lister-id item-dex {:thumbnail thumbnail}]
                                            [common/list-item-primary-cell lister-id item-dex {:label name :stretch? true
                                                                                               :placeholder :unnamed-vehicle}]
                                            [common/list-item-detail    lister-id item-dex {:content   timestamp :width "160px"}]
                                            [common/list-item-end-icon  lister-id item-dex {:icon      :navigate_next}]]}]))

(defn- vehicle-item
  [lister-id item-dex {:keys [id] :as vehicle-item}]
  [elements/toggle {:content     [vehicle-item-structure lister-id item-dex vehicle-item]
                    :hover-color :highlight
                    :on-click    [:router/go-to! (str "/@app-home/vehicles/"id)]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- vehicle-lister-body
  []
  [item-lister/body :vehicles.vehicle-lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:vehicles :vehicle-lister/downloaded-items]
                     :ghost-element    #'common/item-lister-body-ghost-view
                     :list-element     #'vehicle-item}])

(defn- vehicle-lister-header
  []
  [common/item-lister-header :vehicles.vehicle-lister
                             {:cells [[common/item-lister-header-spacer :vehicles.vehicle-lister
                                                                        {:width "108px"}]
                                      [common/item-lister-header-cell   :vehicles.vehicle-lister
                                                                        {:label :name          :order-by-key :name :stretch? true}]
                                      [common/item-lister-header-cell   :vehicles.vehicle-lister
                                                                        {:label :last-modified :order-by-key :modified-at :width "160px"}]
                                      [common/item-lister-header-spacer :vehicles.vehicle-lister
                                                                        {:width "36px"}]]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.vehicle-lister])
        create-vehicle-uri (str "/@app-home/vehicles/create")]
       [common/item-lister-create-item-button :vehicles.vehicle-lister
                                              {:disabled?       lister-disabled?
                                               :create-item-uri create-vehicle-uri}]))

(defn- search-block
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.vehicle-lister])]
       [common/item-lister-search-block :vehicles.vehicle-lister
                                        {:disabled?         lister-disabled?
                                         :field-placeholder :search-in-vehicles}]))

(defn- breadcrumbs
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.vehicle-lister])]
       [common/surface-breadcrumbs :vehicles.vehicle-lister/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :vehicles}]
                                    :disabled? lister-disabled?}]))

(defn- label-bar
  []
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? :vehicles.vehicle-lister])]
       [common/surface-label :vehicles.vehicle-lister/view
                             {:disabled? lister-disabled?
                              :label     :vehicles}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- footer
  []
  [common/item-lister-download-info :vehicles.vehicle-lister {}])

(defn- body
  []
  [:div {:style {:display :flex :flex-direction :column-reverse}}
        [:div {:style {:width "100%"}}
              [vehicle-lister-body]]
        [vehicle-lister-header]])

(defn- header
  []
  (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? :vehicles.vehicle-lister])]
          [:<> [:div {:style {:display :flex :justify-content :space-between :flex-wrap :wrap}}
                     [label-bar]
                     [create-item-button]]
               [breadcrumbs]
               [search-block]]
          [common/item-lister-header-ghost-view :vehicles.vehicle-lister {}]))

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [elements/horizontal-separator {:size :xxl}]
        [body]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [footer]]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
