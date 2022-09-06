
(ns app.common.frontend.lister.views
    (:require [app.common.frontend.surface.views :as surface.views]
              [mid-fruits.candy                  :refer [param]]
              [mid-fruits.keyword                :as keyword]
              [x.app-components.api              :as components]
              [x.app-core.api                    :as a]
              [x.app-elements.api                :as elements]))

;; -- List-item components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn list-item-thumbnail
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:thumbnail (string)}
  ;
  ; @usage
  ;  [common/list-item-thumbnail :my-lister 0 {...}]
  [_ _ {:keys [thumbnail]}]
  [elements/thumbnail {:border-radius :s :height :s :indent {:horizontal :xxs :vertical :xs}
                       :uri thumbnail :width :l}])

(defn list-item-thumbnail-icon
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:icon (keyword)
  ;   :icon-family (keyword)(opt)}
  ;
  ; @usage
  ;  [common/list-item-thumbnail-icon :my-lister 0 {...}]
  [_ _ {:keys [icon icon-family]}]
  (if icon-family [elements/icon {:icon icon :icon-family icon-family :indent {:horizontal :m :vertical :xl}}]
                  [elements/icon {:icon icon                          :indent {:horizontal :m :vertical :xl}}]))

(defn list-item-label
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:content (metamorphic-content)
  ;   :stretch? (boolean)(opt)}
  ;
  ; @usage
  ;  [common/list-item-label :my-lister 0 {...}]
  [_ _ {:keys [content stretch?]}]
  [:div (if stretch? {:style {:flex-grow 1}})
        [elements/label {:color "#333" :content content :indent {:horizontal :xs :right :xs}}]])

(defn list-item-detail
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:content (metamorphic-content)
  ;   :width (string)(opt)}
  ;
  ; @usage
  ;  [common/list-item-detail :my-lister 0 {...}]
  [_ _ {:keys [content width]}]
  [:div {:style {:width width}}
        [elements/label {:color "#555" :content content :font-size :xs :indent {:horizontal :xs :right :xs}}]])

(defn list-item-primary-cell
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:description (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :stretch? (boolean)(opt)
  ;   :timestamp (string)(opt)}
  ;
  ; @usage
  ;  [common/list-item-primary-cell :my-lister 0 {...}]
  [_ _ {:keys [description label stretch? timestamp]}]
  [:div (if stretch? {:style {:flex-grow 1}})
        (if label       [elements/label {:content label                      :indent {:right :xs} :style {:color "#333" :line-height "21px"}}])
        (if timestamp   [elements/label {:content timestamp   :font-size :xs :indent {:right :xs} :style {:color "#888" :line-height "18px"}}])
        (if description [elements/label {:content description :font-size :xs :indent {:right :xs} :style {:color "#888" :line-height "18px"}}])])

(defn list-item-end-icon
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:icon (keyword)
  ;   :style (map)(opt)}
  ;
  ; @usage
  ;  [common/list-item-end-icon :my-lister 0 {...}]
  [_ _ {:keys [icon style]}]
  [elements/icon {:icon icon :indent {:right :xs} :size :s :style style}])

(defn list-item-structure
  ; @param (keyword) lister-id
  ; @param (integer) item-dex
  ; @param (map) cell-props
  ;  {:cells (components in vector)}
  ;
  ; @usage
  ;  [common/list-item-structure :my-lister 0 {...}]
  ;
  ; @usage
  ;  (defn my-cell [])
  ;  [common/list-item-structure :my-lister 0 {:cells [[my-cell]]}]
  [lister-id item-dex {:keys [cells]}]
  (let [item-last? @(a/subscribe [:item-lister/item-last? lister-id item-dex])]
       (reduce conj [:div {:style {:align-items "center" :border-bottom (if-not item-last? "1px solid #f0f0f0") :display "flex"}}]
                    (param cells))))

;; -- Label-bar components ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn create-item-button
  ; @param (keyword) lister-id
  ; @param (map) bar-props
  ;  {:create-item-uri (string)}
  ;
  ; @usage
  ;  [common/create-item-button :my-lister {...}]
  [lister-id {:keys [create-item-uri]}]
  (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? lister-id])]
          (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? lister-id])]
               [elements/icon-button ::create-item-button
                                     {:border-color  :none
                                      :border-radius :s
                                      :color         :primary
                                      :disabled?     lister-disabled?
                                      :hover-color   :highlight
                                      :on-click      [:router/go-to! create-item-uri]
                                      :preset        :add}])))

(defn item-lister-label
  ; @param (keyword) lister-id
  ; @param (map) bar-props
  ;  {:label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-lister-label :my-lister {...}]
  [lister-id {:keys [label]}]
  (let [first-data-received? @(a/subscribe [:item-lister/first-data-received? lister-id])
        lister-disabled?     @(a/subscribe [:item-lister/lister-disabled?     lister-id])]
       [surface.views/surface-label lister-id
                                    {:disabled?   lister-disabled?
                                     :label       label
                                     :loading?    (not first-data-received?)}]))

(defn item-lister-label-bar
  ; @param (keyword) lister-id
  ; @param (map) bar-props
  ;  {:create-item-uri (string)(opt)
  ;   :label (metamorphic-content)}
  ;
  ; @usage
  ;  [common/item-lister-label-bar :my-lister {...}]
  [lister-id {:keys [create-item-uri] :as bar-props}]
  (if-let [error-mode? @(a/subscribe [:item-lister/get-meta-item lister-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-lister felületen nem jelenik meg!
          [elements/horizontal-polarity ::item-lister-label-bar
                                        {:start-content                     [item-lister-label  lister-id bar-props]
                                         :end-content   (if create-item-uri [create-item-button lister-id bar-props])}]))

;; -- Search-block components -------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-lister-search-field
  ; @param (keyword) lister-id
  ; @param (map) block-props
  ;  {:field-placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-lister-search-field :my-lister {...}]
  [lister-id {:keys [field-placeholder]}]
  (if-let [first-data-received? @(a/subscribe [:item-lister/first-data-received? lister-id])]
          (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? lister-id])
                search-event [:item-lister/search-items! lister-id {:search-keys [:name]}]]
               [elements/search-field ::search-items-field
                                      {:autoclear?    true
                                       :autofocus?    true
                                       :disabled?     lister-disabled?
                                       :indent        {:left :xs :right :xxs :top :s}
                                       :on-empty      search-event
                                       :on-type-ended search-event
                                       :placeholder   field-placeholder}])
          [elements/ghost {:height :l :indent {:left :xs :right :xxs :top :s}}]))

(defn item-lister-search-description
  ; @param (keyword) lister-id
  ; @param (map) block-props
  ;
  ; @usage
  ;  [common/item-lister-search-description :my-lister {...}]
  [lister-id _]
  [:div {:style {:display :flex :justify-content :right}}
        (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled?   lister-id])
              all-item-count   @(a/subscribe [:item-lister/get-all-item-count lister-id])]
             [elements/label ::search-items-description
                             {:color     :muted
                              :content   (if-not lister-disabled? (components/content {:content :search-results-n :replacements [all-item-count]}))
                              :font-size :xxs
                              :indent    {:top :xs :vertical :xxs}}])])

(defn item-lister-search-block
  ; @param (keyword) lister-id
  ; @param (map) block-props
  ;  {:field-placeholder (metamorphic-content)(opt)}
  ;
  ; @usage
  ;  [common/item-lister-search-block :my-lister {...}]
  [lister-id block-props]
  (if-let [error-mode? @(a/subscribe [:item-lister/get-meta-item lister-id :error-mode?])]
          [:<>] ; A komponens {:error-mode? true} állapotú item-lister felületen nem jelenik meg!
          [:<> [item-lister-search-field       lister-id block-props]
               [item-lister-search-description lister-id block-props]]))

;; -- Header components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-lister-header-spacer
  ; @param (keyword) lister-id
  ; @param (map) spacer-props
  ;  {:width (string)}
  ;
  ; @usage
  ;  [common/item-lister-header-spacer :my-lister {...}]
  [_ {:keys [width]}]
  [:div {:style {:width width}}])

(defn item-lister-header-cell
  ; @param (keyword) lister-id
  ; @param (map) cell-props
  ;  {:label (metamorphic-content)
  ;   :order-by-key (namespaced keyword)
  ;   :stretch? (boolean)(opt)
  ;   :width (string)(opt)}
  ;
  ; @usage
  ;  [common/item-lister-header-cell :my-lister {...}]
  [lister-id {:keys [label order-by-key stretch? width]}]
  (let [current-order-by @(a/subscribe [:item-lister/get-current-order-by lister-id])
        current-order-by-key       (keyword/get-namespace current-order-by)
        current-order-by-direction (keyword/get-name      current-order-by)]
       [:div {:style {:display "flex" :width width :flex-grow (if stretch? 1 0)}}
             (if (= order-by-key current-order-by-key)
                 [elements/button {:color            :default
                                   :icon             (case current-order-by-direction :descending :arrow_drop_down :ascending :arrow_drop_up)
                                   :on-click         [:item-lister/swap-items! lister-id]
                                   :label            label
                                   :font-size        :xs
                                   :horizontal-align :left
                                   :icon-position    :right
                                   :indent           {:horizontal :xxs}}]
                 [elements/button {:color            :muted
                                   :icon             :arrow_drop_down
                                   :on-click         [:item-lister/order-items! lister-id (keyword/add-namespace order-by-key :descending)]
                                   :label            label
                                   :font-size        :xs
                                   :horizontal-align :left
                                   :icon-position    :right
                                   :indent           {:horizontal :xxs}}])]))

(defn item-lister-header
  ; @param (keyword) lister-id
  ; @param (map) header-props
  ;  {:cells (maps in vector)
  ;    [{:label (metamorphic-content)
  ;      :order-by-key (namespaced keyword)
  ;      :stretch? (boolean)(opt)
  ;      :width (string)(opt)}]}
  ;
  ; @usage
  ;  [common/item-lister-header :my-lister {...}]
  [lister-id {:keys [cells]}]
  (if-let [data-received? @(a/subscribe [:item-lister/data-received? lister-id])]
          (letfn [(f [wrapper cell] (conj wrapper cell))]
                 (reduce f [:div {:style {:background-color "white"  :border-bottom "1px solid #ddd" :display "flex"
                                          :position         "sticky" :top           "48px"}}]
                           (param cells)))))

(defn item-lister-wrapper
  ; @param (keyword) lister-id
  ; @param (map) wrapper-props
  ;  {:item-list (symbol)
  ;   :item-list-header (symbol)}
  ;
  ; @usage
  ;  [common/item-lister-wrapper :my-lister {...}]
  ;
  ; @usage
  ;  (defn my-item-list [])
  ;  (defn my-item-list-header [])
  ;  [common/item-lister-wrapper :my-lister {:item-list        #'my-item-list
  ;                                          :item-list-header #'my-item-list-header}]
  [_ {:keys [item-list item-list-header]}]
  ; Az item-list-header komponenst szükséges a DOM-fában később elhelyezni, mint az item-list komponenst,
  ; így biztosítható, hogy görgetéskor a {position: sticky} pozícionálással megjelenített item-list-header
  ; komponens az item-list komponens felett (z-tengely) jelenjen meg.
  ;
  ; A {:flex-direction :column-reverse} beállítás használatával kiküszöbölhető, hogy a {z-index: ...}
  ; css tulajdonságot kelljen használni (𖤐 z-index 𖤐 666 𖤐 satan 𖤐)
  [:div {:style {:display :flex :flex-direction :column-reverse}}
        [:div {:style {:width "100%"}}
              [item-list]]
        [item-list-header]])

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-lister-ghost-view
  ; @param (keyword) lister-id
  ; @param (map) view-props
  ;  {:padding (string)(opt)}
  ;
  ; @usage
  ;  [common/item-lister-ghost-view :my-lister {...}]
  [_ {:keys [padding]}]
  [:div {:style {:width "100%" :display "flex" :flex-direction "column" :padding padding}}
        [elements/ghost {:height :l :style {:flex-grow :1} :indent {}}]
        [elements/ghost {:height :l :style {:flex-grow :1} :indent {:top :xs}}]
        [elements/ghost {:height :l :style {:flex-grow :1} :indent {:top :xs}}]])
