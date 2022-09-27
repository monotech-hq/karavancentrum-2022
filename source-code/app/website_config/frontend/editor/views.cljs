
(ns app.website-config.frontend.editor.views
    (:require [app.common.frontend.api  :as common]
              [app.storage.frontend.api :as storage]
              [forms.api                :as forms]
              [layouts.surface-a.api    :as surface-a]
              [mid-fruits.css           :as css]
              [mid-fruits.vector        :as vector]
              [plugins.file-editor.api  :as file-editor]
              [x.app-core.api           :as a]
              [x.app-elements.api       :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- company-logo-picker
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [storage/media-picker ::company-logo-picker
                             {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:top :l :vertical :xs}
                              :label         :logo
                              :multi-select? false
                              :toggle-label  :select-image!
                              :thumbnails    {:height :2xl :width :4xl}
                              :value-path    [:website-config :config-editor/edited-item :company-logo-uri]}]))

(defn- company-slogan-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/text-field ::company-slogan-field
                            {:disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :slogan
                             :min-width  :xs
                             :placeholder :the-companys-slogan
                             :value-path [:website-config :config-editor/edited-item :company-slogan]}]))

(defn- company-name-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/text-field ::company-name-field
                            {:autofocus? true
                             :disabled?  editor-disabled?
                             :indent     {:top :l :vertical :xs}
                             :label      :company-name
                             :min-width  :xs
                             :placeholder :the-companys-name
                             :value-path [:website-config :config-editor/edited-item :company-name]}]))

(defn- company-info
  []
  [:div (forms/form-row-attributes)
        [:div (forms/form-block-attributes {:ratio 100})
              [company-logo-picker]
              [company-name-field]
              [company-slogan-field]]])

(defn- basic-info
  []
  [company-info])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- duplicate-contact-group-button
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/button {:color     :primary
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :xs :bottom :xxs}
                         :label     :duplicate!
                         :on-click  [:db/apply-item! [:website-config :config-editor/edited-item :contact-groups]
                                                     vector/duplicate-nth-item group-dex]}]))

(defn- delete-contact-group-button
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/button {:color     :warning
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :xs :bottom :xxs}
                         :label     :delete!
                         :on-click  [:db/apply-item! [:website-config :config-editor/edited-item :contact-groups]
                                                     vector/remove-nth-item group-dex]}]))

(defn- contact-group-label-field
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/text-field {:autofocus?  true
                             :disabled?   editor-disabled?
                             :label       :label
                             :indent      {:all :xs}
                             :placeholder :contacts-label-eg
                             :value-path  [:website-config :config-editor/edited-item :contact-groups group-dex :label]}]))

(defn- email-addresses-field
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-field {:disabled?   editor-disabled?
                              :label       :email-address
                              :indent      {:all :xs}
                              :placeholder :email-address-eg
                              :value-path  [:website-config :config-editor/edited-item :contact-groups group-dex :email-addresses]}]))

(defn- phone-numbers-field
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-field {:disabled?   editor-disabled?
                              :label       :phone-number
                              :indent      {:all :xs}
                              :placeholder :phone-number-eg
                              :value-path  [:website-config :config-editor/edited-item :contact-groups group-dex :phone-numbers]}]))

(defn- contact-group
  [group-dex group-props]
  [:<> [elements/horizontal-separator {:size :l}]
       [:div {:style {:background-color (css/var "background-color-highlight")
                      :border-radius    (css/var "border-radius-m")
                      :margin "0 12px"}}
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 100})
                         [contact-group-label-field group-dex group-props]]]
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 50})
                         [phone-numbers-field group-dex group-props]]
                   [:div (forms/form-block-attributes {:ratio 50})
                         [email-addresses-field group-dex group-props]]]
             [:div {:style {:display :flex :justify-content :flex-end}}
                   [duplicate-contact-group-button group-dex group-props]
                   [delete-contact-group-button    group-dex group-props]]]])

(defn- contact-groups
  []
  (letfn [(f [%1 %2 %3] (conj %1 [contact-group %2 %3]))]
         (let [contact-groups @(a/subscribe [:db/get-item [:website-config :config-editor/edited-item :contact-groups]])]
              (reduce-kv f [:<>] contact-groups))))

(defn- contact-group-action-bar
  []
  [common/file-editor-action-bar :website-config
                                 {:label    :add-contacts-data!
                                  :on-click [:db/apply-item! [:website-config :config-editor/edited-item :contact-groups]
                                                             vector/cons-item {}]}])

(defn- contacts-data
  []
  [:<> [contact-group-action-bar]
       [contact-groups]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- duplicate-address-group-button
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/button {:color     :primary
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :xs :bottom :xxs}
                         :label     :duplicate!
                         :on-click  [:db/apply-item! [:website-config :config-editor/edited-item :address-groups]
                                                     vector/duplicate-nth-item group-dex]}]))

(defn- delete-address-group-button
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/button {:color     :warning
                         :disabled? editor-disabled?
                         :font-size :xs
                         :indent    {:right :xs :bottom :xxs}
                         :label     :delete!
                         :on-click  [:db/apply-item! [:website-config :config-editor/edited-item :address-groups]
                                                     vector/remove-nth-item group-dex]}]))

(defn- address-group-label-field
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/text-field {:autofocus?  true
                             :disabled?   editor-disabled?
                             :label       :label
                             :indent      {:all :xs}
                             :placeholder :address-label-eg
                             :value-path  [:website-config :config-editor/edited-item :address-groups group-dex :label]}]))

(defn- company-addresses-field
  [group-dex _]
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-field {:disabled?   editor-disabled?
                              :label       :address
                              :indent      {:all :xs}
                              :placeholder :address-eg
                              :value-path  [:website-config :config-editor/edited-item :contact-groups group-dex :company-addresses]}]))

(defn- address-group
  [group-dex group-props]
  [:<> [elements/horizontal-separator {:size :l}]
       [:div {:style {:background-color (css/var "background-color-highlight")
                      :border-radius    (css/var "border-radius-m")
                      :margin "0 12px"}}
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 100})
                         [address-group-label-field group-dex group-props]]]
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 100})
                         [company-addresses-field group-dex group-props]]]
             [:div {:style {:display :flex :justify-content :flex-end}}
                   [duplicate-address-group-button group-dex group-props]
                   [delete-address-group-button    group-dex group-props]]]])

(defn- address-groups
  []
  (letfn [(f [%1 %2 %3] (conj %1 [address-group %2 %3]))]
         (let [address-groups @(a/subscribe [:db/get-item [:website-config :config-editor/edited-item :address-groups]])]
              (reduce-kv f [:<>] address-groups))))

(defn- address-group-action-bar
  []
  [common/file-editor-action-bar :website-config
                                 {:label    :add-address-data!
                                  :on-click [:db/apply-item! [:website-config :config-editor/edited-item :address-groups]
                                                             vector/cons-item {}]}])

(defn- address-data
  []
  [:<> [address-group-action-bar]
       [address-groups]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- facebook-links-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-field ::facebook-links-field
                             {:autofocus? true
                              :disabled?  editor-disabled?
                              :indent     {:vertical :xs :top :l}
                              :label      :facebook-link
                              :placeholder "facebook.com/example"
                              :value-path [:website-config :config-editor/edited-item :facebook-links]}]))

(defn- instagram-links-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-field ::instagram-links-field
                             {:disabled?  editor-disabled?
                              :indent     {:vertical :xs :top :l}
                              :label      :instagram-link
                              :placeholder "instagram.com/example"
                              :value-path [:website-config :config-editor/edited-item :instagram-links]}]))

(defn- youtube-links-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-field ::youtube-links-field
                             {:disabled?   editor-disabled?
                              :indent      {:vertical :xs :top :l}
                              :label       :youtube-link
                              :placeholder "youtube.com/channel/example"
                              :value-path  [:website-config :config-editor/edited-item :youtube-links]}]))

(defn- social-media
  []
  [:<> [facebook-links-field]
       [instagram-links-field]
       [youtube-links-field]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- meta-name-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/text-field ::meta-name-field
                            {:autofocus?  true
                             :disabled?   editor-disabled?
                             :label       :meta-name
                             :indent      {:vertical :xs :top :l}
                             :info-text   :describe-the-page-with-a-name
                             :placeholder :meta-name-eg
                             :value-path  [:website-config :config-editor/edited-item :meta-name]}]))

(defn- meta-title-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/text-field ::meta-title-field
                            {:disabled?   editor-disabled?
                             :label       :meta-title
                             :indent      {:vertical :xs :top :l}
                             :info-text   :describe-the-page-with-a-short-title
                             :placeholder :meta-title-eg
                             :value-path  [:website-config :config-editor/edited-item :meta-title]}]))

(defn- meta-description-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multiline-field ::meta-description-field
                                 {:disabled?   editor-disabled?
                                  :label       :meta-description
                                  :indent      {:vertical :xs :top :l}
                                  :info-text   :describe-the-page-with-a-short-description
                                  :placeholder :meta-description-eg
                                  :value-path  [:website-config :config-editor/edited-item :meta-description]}]))

(defn- meta-keywords-field
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [elements/multi-combo-box ::meta-keywords-field
                                 {:deletable?  true
                                  :disabled?   editor-disabled?
                                  :label       :meta-keywords
                                  :indent      {:vertical :xs :top :l}
                                  :info-text   :describe-the-page-in-a-few-keywords
                                  :placeholder :meta-keywords-eg
                                  :value-path  [:website-config :config-editor/edited-item :meta-keywords]}]))

(defn- meta-details
  []
  [:<> [meta-name-field]
       [meta-title-field]
       [meta-keywords-field]
       [meta-description-field]])

(defn- seo
  []
  [meta-details])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- share-preview-picker
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [storage/media-picker ::share-preview-picker
                             {:autosave?     true
                              :disabled?     editor-disabled?
                              :extensions    ["bmp" "jpg" "jpeg" "png" "webp"]
                              :indent        {:top :l :vertical :xs}
                              :info-text     {:content :recommended-image-size-n :replacements ["1200" "630"]}
                              :label         :share-preview
                              :multi-select? false
                              :toggle-label  :select-image!
                              :thumbnails    {:height :2xl :width :4xl}
                              :value-path    [:website-config :config-editor/edited-item :share-preview-uri]}]))

(defn- share
  []
  [:<> [share-preview-picker]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [common/file-editor-menu-bar :website-config
                                    {:menu-items [{:label :basic-info    :change-keys [:company-name :company-slogan :company-logo]}
                                                  {:label :contacts-data :change-keys [:contact-groups]}
                                                  {:label :address-data  :change-keys [:address-groups]}
                                                  {:label :social-media  :change-keys [:facebook-links :instagram-links :youtube-links]}
                                                  {:label :seo           :change-keys [:meta-name :meta-title :meta-keywords :meta-description]}
                                                  {:label :share         :change-keys [:share-preview]}]
                                     :disabled? editor-disabled?}]))

(defn- view-selector
  []
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-config])]
       (case current-view-id :basic-info    [basic-info]
                             :contacts-data [contacts-data]
                             :address-data  [address-data]
                             :social-media  [social-media]
                             :seo           [seo]
                             :share         [share])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- control-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [common/file-editor-control-bar :website-config
                                       {:disabled? editor-disabled?}]))

(defn- breadcrumbs
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [common/surface-breadcrumbs :website-config/view
                                   {:crumbs [{:label :app-home :route "/@app-home"}
                                             {:label :website-config}]
                                    :disabled? editor-disabled?}]))

(defn- label-bar
  []
  (let [editor-disabled? @(a/subscribe [:file-editor/editor-disabled? :website-config])]
       [common/surface-label :website-config/view
                             {:disabled? editor-disabled?
                              :label     :website-config}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]])

(defn- view-structure
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [header]
        [view-selector]
        [elements/horizontal-separator {:size :xxl}]
        [:div {:style {:flex-grow "1" :display "flex" :align-items "flex-end"}}
              [control-bar]]])

(defn- website-config-editor
  []
  [file-editor/body :website-config
                    {:content-path  [:website-config :config-editor/edited-item]
                     :form-element  #'view-structure
                     :ghost-element #'common/file-editor-ghost-view}])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'website-config-editor}])
