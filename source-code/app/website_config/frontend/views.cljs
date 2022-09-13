
(ns app.website-config.frontend.views
    (:require [app.storage.frontend.api]
              [app.common.frontend.api :as common]
              [forms.api               :as forms]
              [layouts.surface-a.api   :as surface-a]
              [mid-fruits.css          :as css]
              [mid-fruits.vector       :as vector]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- company-logo-button
  []
  [elements/button ::company-logo-button
                   {:color     :muted
                    :font-size :xs
                    :indent    {:left :xs}
                    :label     :select-image!
                    :on-click [:storage.media-selector/load-selector! :website-config/logo-selector
                                                                      {:value-path [:website-config :config-handler/edited-item :company-logo-uri]}]}])

(defn- company-logo-label
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/label ::company-logo-label
                       {:content :logo
                        :disabled? synchronizing?
                        :indent {:left :xs :top :xxl}}]))

(defn- company-logo-thumbnail
  []
  (let [company-logo-uri @(a/subscribe [:db/get-item [:website-config :config-handler/edited-item :company-logo-uri]])
        synchronizing?   @(a/subscribe [:website-config/synchronizing?])]
       [elements/thumbnail ::company-logo-thumbnail
                           {:border-radius :m
                            :disabled?     synchronizing?
                            :indent        {:left :xs}
                            :height        :xxl
                            :width         :xxl
                            :uri           company-logo-uri}]))

(defn- company-logo
  []
  [:<> [company-logo-label]
       [company-logo-thumbnail]
       [:div {:style {:display :flex}}
             [company-logo-button]]])

(defn- company-slogan-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field ::company-slogan-field
                            {:disabled?  synchronizing?
                             :indent     {:top :xs :vertical :xs}
                             :label      :slogan
                             :min-width  :xs
                             :placeholder :the-companys-slogan
                             :value-path [:website-config :config-handler/edited-item :company-slogan]}]))

(defn- company-name-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field ::company-name-field
                            {:autofocus? true
                             :disabled?  synchronizing?
                             :indent     {:top :xxl :vertical :xs}
                             :label      :company-name
                             :min-width  :xs
                             :placeholder :the-companys-name
                             :value-path [:website-config :config-handler/edited-item :company-name]}]))

(defn- company-info
  []
  [:div (forms/form-row-attributes)
        [:div (forms/form-block-attributes {:ratio 100})
              [company-logo]
              [company-name-field]
              [company-slogan-field]]])

(defn- basic-info
  []
  [company-info])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- delete-contact-group-icon-button
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/icon-button {:disabled?   synchronizing?
                              :icon        :delete
                              :icon-family :material-icons-outlined
                              :color       :warning
                              :indent      {:right :xs :top :xxs}
                              :on-click    [:db/apply-item! [:website-config :config-handler/edited-item :contact-groups]
                                                            vector/remove-nth-item group-dex]}]))

(defn- contact-group-label-field
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field {:autofocus?  true
                             :disabled?   synchronizing?
                             :label       :label
                             :indent      {:all :xs}
                             :placeholder :contacts-label-eg
                             :value-path  [:website-config :config-handler/edited-item :contact-groups group-dex :label]}]))

(defn- email-addresses-field
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-field {:disabled?   synchronizing?
                              :label       :email-address
                              :indent      {:all :xs}
                              :placeholder :email-address-eg
                              :value-path  [:website-config :config-handler/edited-item :contact-groups group-dex :email-addresses]}]))

(defn- phone-numbers-field
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-field {:disabled?   synchronizing?
                              :label       :phone-number
                              :indent      {:all :xs}
                              :placeholder :phone-number-eg
                              :value-path  [:website-config :config-handler/edited-item :contact-groups group-dex :phone-numbers]}]))

(defn- contact-group
  [group-dex group-props]
  [:<> [elements/horizontal-separator {:size :xxl}]
       [:div {:style {:background-color (css/var "background-color-highlight")
                      :border-radius    (css/var "border-radius-m")
                      :margin "0 12px"}}
             [:div (forms/form-row-attributes)
                   [:div {:style {:flex-grow 1}}
                         [contact-group-label-field group-dex group-props]]
                   [:div {}
                         [delete-contact-group-icon-button group-dex group-props]]]
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 50})
                         [phone-numbers-field group-dex group-props]]
                   [:div (forms/form-block-attributes {:ratio 50})
                         [email-addresses-field group-dex group-props]]]]])

(defn- contact-groups
  []
  (letfn [(f [%1 %2 %3] (conj %1 [contact-group %2 %3]))]
         (let [contact-groups @(a/subscribe [:db/get-item [:website-config :config-handler/edited-item :contact-groups]])]
              (reduce-kv f [:<>] contact-groups))))

(defn- add-contact-group-button
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/button ::add-contact-group-button
                        {:color         :primary
                         :disabled?     synchronizing?
                         :font-size     :xs
                         :icon          :add
                         :icon-position :right
                         :indent        {:right :xs :top :xxs}
                         :label         :add-contacts-data!
                         :on-click      [:db/apply-item! [:website-config :config-handler/edited-item :contact-groups] vector/cons-item {}]}]))

(defn- add-contact-group-bar
  []
  [elements/row ::add-contact-group-bar
                {:content [add-contact-group-button]
                 :horizontal-align :right}])

(defn- contacts-data
  []
  [:<> [add-contact-group-bar]
       [contact-groups]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- delete-address-group-icon-button
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/icon-button {:disabled?   synchronizing?
                              :icon        :delete
                              :icon-family :material-icons-outlined
                              :color       :warning
                              :indent      {:right :xs :top :xxs}
                              :on-click    [:db/apply-item! [:website-config :config-handler/edited-item :address-groups]
                                                            vector/remove-nth-item group-dex]}]))

(defn- address-group-label-field
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field {:autofocus?  true
                             :disabled?   synchronizing?
                             :label       :label
                             :indent      {:all :xs}
                             :placeholder :address-label-eg
                             :value-path  [:website-config :config-handler/edited-item :address-groups group-dex :label]}]))

(defn- company-addresses-field
  [group-dex _]
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-field {:disabled?   synchronizing?
                              :label       :address
                              :indent      {:all :xs}
                              :placeholder :address-eg
                              :value-path  [:website-config :config-handler/edited-item :contact-groups group-dex :company-addresses]}]))

(defn- address-group
  [group-dex group-props]
  [:<> [elements/horizontal-separator {:size :xxl}]
       [:div {:style {:background-color (css/var "background-color-highlight")
                      :border-radius    (css/var "border-radius-m")
                      :margin "0 12px"}}
             [:div (forms/form-row-attributes)
                   [:div {:style {:flex-grow 1}}
                         [address-group-label-field group-dex group-props]]
                   [:div {}
                         [delete-address-group-icon-button group-dex group-props]]]
             [:div (forms/form-row-attributes)
                   [:div (forms/form-block-attributes {:ratio 100})
                         [company-addresses-field group-dex group-props]]]]])

(defn- address-groups
  []
  (letfn [(f [%1 %2 %3] (conj %1 [address-group %2 %3]))]
         (let [address-groups @(a/subscribe [:db/get-item [:website-config :config-handler/edited-item :address-groups]])]
              (reduce-kv f [:<>] address-groups))))

(defn- add-address-group-button
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/button ::add-address-group-button
                        {:color         :primary
                         :disabled?     synchronizing?
                         :font-size     :xs
                         :icon          :add
                         :icon-position :right
                         :indent        {:right :xs :top :xxs}
                         :label         :add-address-data!
                         :on-click      [:db/apply-item! [:website-config :config-handler/edited-item :address-groups] vector/cons-item {}]}]))

(defn- add-address-group-bar
  []
  [elements/row ::add-address-group-bar
                {:content [add-address-group-button]
                 :horizontal-align :right}])

(defn- address-data
  []
  [:<> [add-address-group-bar]
       [address-groups]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- facebook-links-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-field ::facebook-links-field
                             {:autofocus? true
                              :disabled?  synchronizing?
                              :indent     {:vertical :xs :top :xxl}
                              :label      :facebook-link
                              :placeholder "facebook.com/example"
                              :value-path [:website-config :config-handler/edited-item :facebook-links]}]))

(defn- instagram-links-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-field ::instagram-links-field
                             {:disabled?  synchronizing?
                              :indent     {:vertical :xs :top :xxl}
                              :label      :instagram-link
                              :placeholder "instagram.com/example"
                              :value-path [:website-config :config-handler/edited-item :instagram-links]}]))

(defn- youtube-links-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-field ::youtube-links-field
                             {:disabled?   synchronizing?
                              :indent      {:vertical :xs :top :xxl}
                              :label       :youtube-link
                              :placeholder "youtube.com/channel/example"
                              :value-path  [:website-config :config-handler/edited-item :youtube-links]}]))

(defn- social-media
  []
  [:<> [facebook-links-field]
       [instagram-links-field]
       [youtube-links-field]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- meta-name-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field ::meta-name-field
                            {:autofocus?  true
                             :disabled?   synchronizing?
                             :label       :meta-name
                             :indent      {:vertical :xs :top :xxl}
                             :info-text   :describe-the-page-with-a-name
                             :placeholder :meta-name-eg
                             :value-path  [:website-config :config-handler/edited-item :meta-name]}]))

(defn- meta-title-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/text-field ::meta-title-field
                            {:disabled?   synchronizing?
                             :label       :meta-title
                             :indent      {:vertical :xs :top :xxl}
                             :info-text   :describe-the-page-with-a-short-title
                             :placeholder :meta-title-eg
                             :value-path  [:website-config :config-handler/edited-item :meta-title]}]))

(defn- meta-description-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multiline-field ::meta-description-field
                                 {:disabled?   synchronizing?
                                  :label       :meta-description
                                  :indent      {:vertical :xs :top :xxl}
                                  :info-text   :describe-the-page-with-a-short-description
                                  :placeholder :meta-description-eg
                                  :value-path  [:website-config :config-handler/edited-item :meta-description]}]))

(defn- meta-keywords-field
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/multi-combo-box ::meta-keywords-field
                                 {:deletable?  true
                                  :disabled?   synchronizing?
                                  :label       :meta-keywords
                                  :indent      {:vertical :xs :top :xxl}
                                  :info-text   :describe-the-page-in-a-few-keywords
                                  :placeholder :meta-keywords-eg
                                  :value-path  [:website-config :config-handler/edited-item :meta-keywords]}]))

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

(defn- share-preview-button
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/button ::share-preview-button
                        {:color     :muted
                         :disabled? synchronizing?
                         :font-size :xs
                         :indent    {:left :xs}
                         :label     :select-image!
                         :on-click [:storage.media-selector/load-selector! :website-config/logo-selector
                                                                           {:value-path [:website-config :config-handler/edited-item :share-preview-uri]}]}]))

(defn- share-preview-label
  []
  (let [synchronizing? @(a/subscribe [:website-config/synchronizing?])]
       [elements/label ::share-preview-label
                       {:content   :share-preview
                        :disabled? synchronizing?
                        :indent    {:left :xs :top :xxl}
                        :info-text {:content :recommended-image-size-n :replacements ["1200" "630"]}}]))

(defn- share-preview-thumbnail
  []
  (let [share-preview-uri @(a/subscribe [:db/get-item [:website-config :config-handler/edited-item :share-preview-uri]])
        synchronizing?    @(a/subscribe [:website-config/synchronizing?])]
       [elements/thumbnail ::share-preview-thumbnail
                           {:border-radius :m
                            :disabled?     synchronizing?
                            :height        :4xl
                            :width         :4xl
                            :indent        {:left :xs}
                            :uri           share-preview-uri}]))

(defn- share
  []
  [:<> [share-preview-label]
       [share-preview-thumbnail]
       [:div {:style {:display :flex}}
             [share-preview-button]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-view
  []
  [:<> [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
       [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
       [elements/ghost {:height :l :indent {:top :xs :vertical :xs}}]
       [elements/ghost {           :indent {:top :xs :vertical :xs} :style {:height "96px"}}]])

(defn- view-selector
  []
  (if-let [loaded? @(a/subscribe [:website-config/loaded?])]
          (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-config])]
               (case current-view-id :basic-info    [basic-info]
                                     :contacts-data [contacts-data]
                                     :address-data  [address-data]
                                     :social-media  [social-media]
                                     :seo           [seo]
                                     :share         [share]))
          [ghost-view]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- menu-bar-item
  [{:keys [change-keys label view-id]}]
  (let [current-view-id @(a/subscribe [:gestures/get-current-view-id :website-config])
        form-changed?   @(a/subscribe [:website-config/form-changed? change-keys])]
       {:active?     (= current-view-id view-id)
        :badge-color (if form-changed? :primary)
        :label       label
        :on-click    [:gestures/change-view! :website-config view-id]}))

(defn- menu-bar
  []
  (if-let [loaded? @(a/subscribe [:website-config/loaded?])]
          [:<> [elements/menu-bar ::menu-bar
                                  {:menu-items [(menu-bar-item {:change-keys [:company-name :company-slogan :company-logo]
                                                                :label   :basic-info
                                                                :view-id :basic-info})
                                                (menu-bar-item {:change-keys [:contact-groups]
                                                                :label   :contacts-data
                                                                :view-id :contacts-data})
                                                (menu-bar-item {:change-keys [:address-groups]
                                                                :label   :address-data
                                                                :view-id :address-data})
                                                (menu-bar-item {:change-keys [:facebook-links :instagram-links :youtube-links]
                                                                :label   :social-media
                                                                :view-id :social-media})
                                                (menu-bar-item {:change-keys [:meta-name :meta-title :meta-keywords :meta-description]
                                                                :label   :seo
                                                                :view-id :seo})
                                                (menu-bar-item {:change-keys [:share-preview]
                                                                :label   :share
                                                                :view-id :share})]}]
               [elements/horizontal-line {:color :highlight :indent {:vertical :xs}}]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  (let [loaded? @(a/subscribe [:website-config/loaded?])]
       [common/surface-breadcrumbs :website-config/view
                                   {:crumbs [{:label :app-home
                                              :route "/@app-home"}
                                             {:label :website-config}]
                                    :loading? (not loaded?)}]))

(defn- revert-icon-button
  []
  (let [config-changed? @(a/subscribe [:website-config/config-changed?])]
       [elements/icon-button ::revert-icon-button
                             {:disabled?   (not config-changed?)
                              :hover-color :highlight
                              :icon        :settings_backup_restore
                              :on-click    [:website-config/revert-changes!]}]))

(defn- save-icon-button
  []
  (let [config-changed? @(a/subscribe [:website-config/config-changed?])]
       [elements/icon-button ::save-icon-button
                             {:disabled?   (not config-changed?)
                              :hover-color :highlight
                              :icon        :save
                              :color       :primary
                              :on-click    [:website-config/save-changes!]}]))

(defn- controls
  []
  (if-let [loaded? @(a/subscribe [:website-config/loaded?])]
          [:<> [revert-icon-button]
               [save-icon-button]]))

(defn- label
  []
  (let [loaded? @(a/subscribe [:website-config/loaded?])]
       [common/surface-label :webwebsite-config/view
                             {:loading? (not loaded?)
                              :label    :website-config}]))

(defn- label-bar
  []
  [elements/horizontal-polarity ::label-bar
                                {:start-content [label]
                                 :end-content   [controls]}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  []
  [:<> [label-bar]
       [breadcrumbs]
       [elements/horizontal-separator {:size :xxl}]
       [menu-bar]
       [view-selector]
       [elements/horizontal-separator {:size :xxl}]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
