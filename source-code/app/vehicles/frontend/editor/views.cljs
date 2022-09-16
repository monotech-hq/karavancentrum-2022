
(ns app.vehicles.frontend.editor.views
    (:require
      [x.app-core.api           :as a]
      [x.app-elements.api       :as elements]
      [layouts.surface-a.api    :as surface-a]
      [forms.api                :as forms]
      [plugins.item-editor.api  :as item-editor]
      [app.common.frontend.api  :as common]
      [app.storage.frontend.api :as storage]

      [app.components.frontend.api :as comp]))

(def default-indent {:vertical :xs})

;; -----------------------------------------------------------------------------
;; ---- Components ----

;; -----------------------------------------------------------------------------
;; ---- Details ----

(defn- name-field []
  (let [editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.editor])]
    [elements/combo-box ::name-field
     {:label        :name
      :placeholder  :name
      :autofocus?   true
      :disabled?    editor-disabled?
      :form-id      :vehicles.editor/form
      :options-path [:vehicles :editor/suggestions :name]
      :value-path   [:vehicles :editor/edited-item :name]}]))

(defn- built-year []
  [elements/text-field ::built-year
    {:form-id    :vehicles.editor/form
     :label      :built-year
     :placeholder "2022"
     :value-path [:vehicles :editor/edited-item :built-year]}])

(defn- seat-number []
  [elements/text-field ::seat-number
    {:form-id    :vehicles.editor/form
     :label      :seat-number
     :placeholder "4"
     :value-path [:vehicles :editor/edited-item :seat-number]}])

(defn- bed-number []
  [elements/text-field ::bed-number
    {:form-id    :vehicles.editor/form
     :label      :bed-number
     :placeholder "2"
     :value-path [:vehicles :editor/edited-item :bed-number]}])

(defn- thumbnail []
  (let [uri              @(a/subscribe [:db/get-item [:vehicles :editor/edited-item :thumbnail]])
        editor-disabled? @(a/subscribe [:item-editor/editor-disabled? :vehicles.editor])]
    [elements/thumbnail ::thumbnail
     {:class :vehicles-details--thumbnail
      :uri           uri
      :disabled?     editor-disabled?
      :border-radius :s
      :style {:height "100%" :width "100%"}
      :on-click  [:storage.media-selector/load-selector! :vehicles.editor/thumbnail-selector
                  {:value-path [:vehicles :editor/edited-item :thumbnail]}]}]))

(defn- details []
  [:<>
   [:div {:style {:display "flex" :gap "25px" :justify-content "center"}}
    [:div {:style {:min-width "320px"}}
     [thumbnail]]
    [:div {:style {:width "50%"}}
     [name-field]
     [built-year]
     [seat-number]
     [bed-number]]]])


;; ---- Details ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Images ----

(defn img [{:keys [item-props]} {:keys [attributes listeners]}]
  [:div (merge attributes listeners
               {:style {:transformOrigin    "0 0"
                        :height "200px"
                        :backgroundSize     "cover"
                        :backgroundPosition "center"
                        :backgroundImage    (str "url(" item-props ")")
                        :border-radius "10px"}})])

(defn images-selector-button []
  [elements/button ::thumbnail
   {:border-radius :s
    :style {:width "200px"}
    :indent default-indent
    :background-color :primary
    :icon :library_add
    :label :choose-images
    :on-click  [:storage.media-selector/load-selector! :vehicles.editor/thumbnail-selector
                {:multiple?  true
                 :value-path [:vehicles :editor/edited-item :images]}]}])

(defn- images []
  (let [items @(a/subscribe [:db/get-item [:vehicles :editor/edited-item :images]])]
    [:div
     [images-selector-button]
     [elements/horizontal-separator {:size :s}]
     [elements/horizontal-line {:size :s}]
     [elements/horizontal-separator {:size :s}]

     (if (empty? items)
       [:p "Üres"]
       [:div {:style {:display "grid" :gap "25px" :grid-template-columns "repeat(auto-fill, minmax(200px, 1fr))"}}
        [comp/sortable {:items      items
                        :value-path [:vehicles :editor/edited-item :images]
                        :item       #'img}]])]))


;; ---- Images ----
;; -----------------------------------------------------------------------------

(defn- tabs []
  [comp/tabs {:view-id :vehicles.editor}
    :details [details]
    :images  [images]])

(defn- ghost-view []
  [common/item-editor-ghost-view :vehicles.editor
                                 {:padding "0 12px"}])

(defn- editor []
  [item-editor/body
   :vehicles.editor
   {:auto-title?      true
    :form-element     #'tabs
    :ghost-element    #'ghost-view
    :form-id          :vehicles.editor/form
    :item-path        [:vehicles :editor/edited-item]
    :label-key        :name
    :suggestion-keys  [:name]
    :suggestions-path [:vehicles :editor/suggestions]}])


(defn- label-bar []
  (let [name @(a/subscribe [:db/get-item [:vehicles :editor/edited-item :name]])]
    [common/item-editor-label-bar
     :vehicles.editor
     {:name             name
      :name-placeholder :unnamed-vehicle}]))

(defn- breadcrumbs []
  (let [loaded? @(a/subscribe [:contents/loaded?])
        vehicle-name @(a/subscribe [:db/get-item [:vehicles :editor/edited-item :name]])]
       [common/surface-breadcrumbs :contents/view
                                   {:crumbs [{:label :app-home
                                              :route "/@app-home"}
                                             {:label :vehicles
                                              :route "/@app-home/vehicles"}
                                             {:label vehicle-name}]
                                    :loading? (not loaded?)}]))

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn- view-structure []
  [:<> [elements/horizontal-separator {:size :s}]
       [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :s}]
       [editor]
       [elements/horizontal-separator {:size :xxl}]])

(defn view [surface-id]
  [surface-a/layout
   surface-id
   {:content #'view-structure}])
