
(ns app.website-impressum.frontend.editor.boxes
    (:require [app.common.frontend.api     :as common]
              [app.components.frontend.api :as components]
              [elements.api                :as elements]
              [forms.api                   :as forms]
              [re-frame.api                :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- company-eu-vat-no-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-eu-vat-no-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :eu-vat-no
                             :placeholder :eu-vat-no-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-eu-vat-no]}]))

(defn- company-vat-no-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-vat-no-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :vat-no
                             :placeholder :vat-no-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-vat-no]}]))

(defn- company-reg-no-field
  []
  (let [editor-disabled? @(r/subscribe [:file-editor/editor-disabled? :website-impressum.editor])]
       [elements/text-field ::company-reg-no-field
                            {:disabled?   editor-disabled?
                             :indent      {:top :m :vertical :s}
                             :label       :company-reg-no
                             :placeholder :company-reg-no-placeholder
                             :value-path  [:website-impressum :editor/edited-item :company-reg-no]}]))

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
                                                          [company-est-year-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-reg-office-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-reg-no-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-vat-no-field]]]
                                              [:div (forms/form-row-attributes)
                                                    [:div (forms/form-block-attributes {:ratio 100})
                                                          [company-eu-vat-no-field]]]
                                              [elements/horizontal-separator {:height :s}]]
                                :disabled? editor-disabled?
                                :label     :company-data}]))
