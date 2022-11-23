
(ns app.website-impressum.frontend.editor.boxes
    (:require [app.common.frontend.api     :as common]
              [app.components.frontend.api :as components]
              [elements.api                :as elements]
              [forms.api                   :as forms]
              [re-frame.api                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- company-reg-number-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-reg-number-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :company-reg-number
                             :placeholder :company-reg-number-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-reg-number]}]))

(defn- company-reg-office-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-reg-office-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :company-reg-office
                             :placeholder :company-reg-office-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-reg-office]}]))

(defn- company-est-year-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-est-year-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :company-est-year
                             :placeholder :company-est-year-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-est-year]}]))

(defn- company-slogan-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-slogan-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :company-slogan
                             :placeholder :company-slogan-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-slogan]}]))

(defn- company-name-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-name-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :company-name
                             :placeholder :company-name-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-name]}]))

(defn- company-data-box
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [components/surface-box ::company-data-box
                               {:content [:<> [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-name-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-slogan-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-est-year-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-reg-office-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-reg-number-field]]]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :label     :company-data}]))
