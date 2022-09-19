
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

(defn- ghost-view
  []
  [common/item-lister-ghost-view :vehicles.vehicle-lister
                                 {:padding "0 12px"}])

(defn- vehicle-lister
  []
  [item-lister/body :vehicles.vehicle-lister
                    {:default-order-by :modified-at/descending
                     :items-path       [:vehicles :vehicle-lister/downloaded-items]
                     :ghost-element    #'ghost-view
                     :list-element     #'vehicle-item}])

(defn- header
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

(defn- wrapper
  []
  [common/item-lister-wrapper :vehicles.vehicle-lister
                              {:item-list        #'vehicle-lister
                               :item-list-header #'header}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- search-block
  []
  [common/item-lister-search-block :vehicles.vehicle-lister
                                   {:field-placeholder :search-in-vehicles}])

(defn- breadcrumbs
  []
  [common/item-lister-breadcrumbs :vehicles.vehicle-lister
                                  {:crumbs [{:label :app-home
                                             :route "/@app-home"}
                                            {:label :vehicles}]}])

(defn- label-bar
  []
  [common/item-lister-label-bar :vehicles.vehicle-lister
                                {:create-item-uri "/@app-home/vehicles/create"
                                 :label           :vehicles}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [search-block]
       [elements/horizontal-separator {:size :xxl}]
       [wrapper]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
