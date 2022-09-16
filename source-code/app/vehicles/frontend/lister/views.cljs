
(ns app.vehicles.frontend.lister.views
  (:require
    [x.app-core.api          :as a]
    [x.app-elements.api      :as elements]
    [layouts.surface-a.api   :as surface-a]
    [plugins.item-lister.api :as item-lister]
    [app.common.frontend.api :as common]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn- item-structure [lister-id item-dex {:keys [modified-at name thumbnail]}]
  (let [timestamp @(a/subscribe [:activities/get-actual-timestamp modified-at])]
    [common/list-item-structure
     lister-id
     item-dex
     {:cells [[common/list-item-thumbnail lister-id item-dex {:thumbnail thumbnail}]
              [common/list-item-label     lister-id item-dex {:content   name      :stretch? true}]
              [common/list-item-detail    lister-id item-dex {:content   timestamp :width "160px"}]
              [common/list-item-end-icon  lister-id item-dex {:icon      :navigate_next}]]}]))

(defn- item [lister-id item-dex {:keys [id] :as item-props}]
  [elements/toggle
   {:content     [item-structure lister-id item-dex item-props]
    :hover-color :highlight
    :on-click    [:router/go-to! (str "/@app-home/vehicles/"id)]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view []
  [common/item-lister-ghost-view
   :vehicles.lister
   {:padding "0 12px"}])

(defn- lister []
  [item-lister/body
   :vehicles.lister
   {:default-order-by :modified-at/descending
    :items-path       [:vehicles :lister/downloaded-items]
    :ghost-element    #'ghost-view
    :list-element     #'item}])

(defn- header []
  [common/item-lister-header
   :vehicles.lister
   {:cells [[common/item-lister-header-spacer :vehicles.lister
                                              {:width "108px"}]
            [common/item-lister-header-cell   :vehicles.lister
                                              {:label :name          :order-by-key :name :stretch? true}]
            [common/item-lister-header-cell   :vehicles.lister
                                              {:label :last-modified :order-by-key :modified-at :width "160px"}]
            [common/item-lister-header-spacer :vehicles.lister
                                              {:width "36px"}]]}])

(defn- wrapper []
  [common/item-lister-wrapper
   :vehicles.lister
   {:item-list        #'lister
    :item-list-header #'header}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- search-block []
  [common/item-lister-search-block
   :vehicles.lister
   {:field-placeholder :search-in-vehicles}])

(defn- label-bar []
  [common/item-lister-label-bar
   :vehicles.lister
   {:create-item-uri "/@app-home/vehicles/create"
    :label           :vehicles}])

(defn- breadcrumbs []
  (let [loaded? @(a/subscribe [:contents/loaded?])]
       [common/surface-breadcrumbs :contents/view
                                   {:crumbs [{:label :app-home
                                              :route "/@app-home"}
                                             {:label :vehicles}]
                                    :loading? (not loaded?)}]))


;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn- view-structure []
  [:<>
       [elements/horizontal-separator {:size :s}]
       [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :s}]
       [search-block]
       [elements/horizontal-separator {:size :s}]
       [wrapper]
       [elements/horizontal-separator {:size :xxl}]])

(defn view [surface-id]
  [surface-a/layout
   surface-id
   {:content #'view-structure}])
